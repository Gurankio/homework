// General libraries and constants.
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <locale.h>
#include <stdarg.h>

// #ifdef WIN32
// #include <windows.h>
// #elif _POSIX_C_SOURCE >= 199309L
// #include <time.h>   // for nanosleep
// #else
// #include <unistd.h> // for usleep
// #endif
//
// void sleep_ms(int milliseconds) { // cross-platform sleep function
// #ifdef WIN32
//   Sleep(milliseconds);
// #elif _POSIX_C_SOURCE >= 199309L
//   struct timespec ts;
//   ts.tv_sec = milliseconds / 1000;
//   ts.tv_nsec = (milliseconds % 1000) * 1000000;
//   nanosleep(&ts, NULL);
// #else
//   usleep(milliseconds * 1000);
// #endif
// }

// Hello World

#ifdef _WIN32
// Windows ONLY libraries and constants.
  #include <windows.h>

  #define TUI_FORMAT     "%c"

  #define TUI_SPACE      32
  #define TUI_VERTICAL   179
  #define TUI_HORIZONTAL 196
  #define TUI_CORNER_TL  218
  #define TUI_CORNER_TR  191
  #define TUI_CORNER_BL  192
  #define TUI_CORNER_BR  217

#else
// Non windows platforms should support wide characters.
  #define TUI_FORMAT     "%s"

  #define TUI_SPACE      " "
  #define TUI_HORIZONTAL "─"
  #define TUI_VERTICAL   "│"
  #define TUI_CORNER_TL  "┌"
  #define TUI_CORNER_TR  "┐"
  #define TUI_CORNER_BL  "└"
  #define TUI_CORNER_BR  "┘"

#endif /* ifdef _WIN32 */

// Main struct.
typedef struct {
  int width, height;
  char *title;

  char **messages;
  int lastMessage;
} tui;

// ANSI constants.
typedef enum {
  ANSI_RED     = 1,
  ANSI_GREEN   = 2,
  ANSI_YELLOW  = 3,
  ANSI_BLUE    = 4,
  ANSI_MAGENTA = 5,
  ANSI_CYAN    = 6,
  ANSI_WHITE   = 7,
  ANSI_DEFAULT = 9
} ANSI_COLORS;

typedef enum {
  ANSI_BOLD          = 1,
  ANSI_LOW_INTENSITY = 2,
  ANSI_UNDERLINE     = 4,
  ANSI_BLINKING      = 5,
  ANSI_REVERSE       = 7,
  ANSI_INVISIBLE     = 8,
  ANSI_RESET         = 9
} ANSI_MODIFIER;

// // Functions prototypes.

void tui_start(tui *, int, int, char *);
void tui_end(tui *);

void tui_dlg_out(tui *, int, int, int, int, char *, char *);
void tui_dlg_in(tui *, int, int, int, int, char *, char *, char *, ...);

void tui_log_out(tui *, char *);
void tui_log_in(tui *, char *, char *, ...);

static void ansi();
static void print_box(int, int, int, int);
static void print_centered_text(int, int, int, char *);
static void print_strings();

static void tui_reprint(tui *);
static void tui_confirm(tui *);

inline char * string_format(char *, ...);
inline char * string_remove_all(char *, const char *);

inline void ansi_xy(int, int);
inline void ansi_clear();
inline void ansi_text_modifier(ANSI_MODIFIER);
inline void ansi_text_color(ANSI_COLORS);
inline void ansi_text_bright_color(ANSI_COLORS);
inline void ansi_background_color(ANSI_COLORS);
inline void ansi_reset();

// // High-level functions.

// Initialize the main struct.
// There must be an afterwork call to tui_end().
void tui_start(tui *tui, int width, int height, char *title) {
  // Constants.
  tui->width = width;
  tui->height = height;
  tui->title = title;

  // Initializing logs
  tui->messages = (char **)malloc(tui->height * sizeof(char *));

  for (int i = 0; i < tui->height; i++)
    tui->messages[i] = "";

  tui->lastMessage = 0;

  ansi();
  tui_reprint(tui);
}

// Free(s) the memory and destroy the main struct?
// Must be called after tui_start()?
void tui_end(tui *tui) {
  tui_confirm(tui);

  // Free Memory
  free(tui->messages);

  // Closing animation?
  //

  // Clears the screen one last time.
  ansi_clear();
}

//
// Create a dialog with the given message.
void tui_dlg_out(tui *tui, int x, int y, int width, int height, char *title, char *message) {
  print_box(x, y, width, height);
  print_centered_text(x, y, width, title);

  // TODO: sanitize input.
  char buffer[strlen(message)];
  strcpy(buffer, message);

  char *split = strtok(buffer, "\n");
  int i;

  for (i = 0; i < height && split != NULL; i++) {
    ansi_xy(x + 1, y + i + 1);
    printf("%s", split);
    split = strtok(NULL, "\n");
  }

  if (i == height && split != NULL) {
    print_centered_text(x, y + height + 1, width, "...");
    tui_confirm(tui);
    tui_dlg_out(tui, x, y, width, height, title, split);
    return;
  }

  // Confirm then redraws.
  tui_confirm(tui);
  tui_reprint(tui);
}

// Create a dialog with the given message calls vscanf();
// Needs to be rewritten.
void tui_dlg_in(tui *tui, int x, int y, int width, int height, char *title, char *message, char *format, ...) {
  print_box(x, y, width, height);
  print_centered_text(x, y, width, title);

  // TODO: sanitize input.
  char buffer[strlen(message)];
  strcpy(buffer, message);

  char *split = strtok(buffer, "\n");
  int i;

  for (i = 0; i < height && split != NULL; i++) {
    ansi_xy(x + 1, y + i + 1);
    printf("%s", split);
    split = strtok(NULL, "\n");
  }

  // if (i == height && split != NULL) {
  //   print_centered_text(x, y + height + 1, width, "...");
  //   tui_confirm(tui);
  //   tui_dlg_out(tui, x, y, width, height, title, split);
  //   return;
  // }

  ansi_xy(x + 1, y + height);
  va_list list;
  va_start(list, format);
  vscanf(format, list);
  va_end(list);

  tui_reprint(tui);
}

//
// Outputs into the main workspace.
void tui_log_out(tui *tui, char *message) {
  //
  if (tui->lastMessage >= tui->height) {
    tui->lastMessage--;

    for (int i = 0; i < tui->height - 1; i++)
      tui->messages[i] = tui->messages[i + 1];
  }

  tui->messages[tui->lastMessage] = message;
  tui->lastMessage++;

  tui_reprint(tui);
}

// Outputs into the main workspace and calls vscanf().
// Needs to be rewritten.
void tui_log_in(tui *tui, char *message, char *format, ...) {
  tui_log_out(tui, message);

  va_list list;
  va_start(list, format);
  vscanf(format, list);
  va_end(list);
}

// // Low-level functions.

// Prints a box
void print_box(int x, int y, int width, int height) {
  ansi_xy(x, y);
  printf(TUI_FORMAT, TUI_CORNER_TL);

  for (int i = 0; i < width; i++)
    printf(TUI_FORMAT, TUI_HORIZONTAL);

  printf(TUI_FORMAT, TUI_CORNER_TR);

  for (int i = 1; i <= height; i++) {
    ansi_xy(x, y + i);
    printf(TUI_FORMAT, TUI_VERTICAL);

    for (int j = 0; j < width; j++)
      printf(TUI_FORMAT, TUI_SPACE);

    printf(TUI_FORMAT, TUI_VERTICAL);
  }

  // bottom
  ansi_xy(x, y + height + 1);
  printf(TUI_FORMAT, TUI_CORNER_BL);

  for (int i = 0; i < width; i++)
    printf(TUI_FORMAT, TUI_HORIZONTAL);

  printf(TUI_FORMAT, TUI_CORNER_BR);
}

// Could be included in box.
void print_centered_text(int x, int y, int width, char *text) {
  ansi_xy(x + (width - strlen(text)) / 2, y);
  printf(TUI_FORMAT, TUI_VERTICAL);
  printf("%s", text);
  printf(TUI_FORMAT, TUI_VERTICAL);
}

//

void print_strings(char **strings, int count) {
  // TODO: sanitize input. EVERYWHERE.

  for (int i = 0; i < count; i++) {
    ansi_xy(3, i + 1);
    printf("%s", strings[i]);
  }
}

// // Utilities functions.

// Activating ANSI escape characters. ONLY FOR WINDOWS
#ifdef _WIN32
void ansi() {
  HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
  DWORD consoleMode;

  GetConsoleMode(hConsole, &consoleMode);
  consoleMode |= 0x004;

  if (!SetConsoleMode(hConsole, consoleMode)) {
    printf("%s\n", "Error during ANSI escape characters activation. Is windows old?");
    return 1;
  }
}

#else
void ansi() {
  // NO-OP
  // There is no need on non windows systems.
}

#endif /* ifdef _WIN32 */


//
void tui_confirm(tui *tui) {
  ansi_xy(0, tui->height + 2);
  ansi_text_modifier(ANSI_INVISIBLE);
  getchar();
  ansi_reset();
}

//
void tui_reprint(tui *tui) {
  ansi_clear();
  print_box(0, 0, tui->width, tui->height);
  print_centered_text(0, 0, tui->width, tui->title);
  print_strings(tui->messages, tui->height);
}

// String Utilities

// Formats a string and returns it.
char * string_format(char *format, ...) {
  char *result = (char *)malloc(10 * sizeof(result));
  va_list list;

  va_start(list, format);
  vsnprintf(result, 10, format, list);
  va_end(list);

  string_remove_all(result, "\n");
  string_remove_all(result, "\r");

  return result;
}

// Remove all occurencies of a string from another string and returns it.
char * string_remove_all(char *str, const char *sub) {
  char *p, *q, *r;

  if ((q = r = strstr(str, sub)) != NULL) {
    size_t len = strlen(sub);

    while ((r = strstr(p = r + len, sub)) != NULL) {
      memmove(q, p, r - p);
      q += r - p;
    }
    memmove(q, p, strlen(p) + 1);
  }

  return str;
}

// ANSI functions.

// Go to column x and row y.
// Y +1 offset is needed to display the first row.
void ansi_xy(int x, int y) {
  printf("\033[%d;%dH", (int)(y + 1), (int)(x));
}

void ansi_clear() {
  printf("\033[2J");
}

void ansi_text_modifier(ANSI_MODIFIER modifier) {
  printf("\033[%dm", modifier);
}

void ansi_text_color(ANSI_COLORS color) {
  printf("\033[3%dm", color);
}

void ansi_text_bright_color(ANSI_COLORS color) {
  printf("\033[9%dm", color);
}

void ansi_background_color(ANSI_COLORS color) {
  printf("\033[4%dm", color);
}

void ansi_reset() {
  printf("\033[0m");
}

// // // // // // //

int main() {
  tui tui;

  tui_start(&tui, 50, 20, "Test");
  ansi_text_color(ANSI_RED); // Needs some fixing. But its pretty cool.
  tui_dlg_out(&tui, 10, 10, 25, 3, "Title", "Hello\nAsdf\nAS{%d}DFGHJK\nasd\nqwerty");
  // Cooler idea: one function when you find {%xx} you call vscanf(). void * should do the trick.

  int i;
  tui_dlg_in(&tui, 10, 10, 25, 3, "Title", "Hello World: ", "%d", &i);
  tui_log_out(&tui, string_format("ASDF%d", i));
  tui_confirm(&tui);
  tui_end(&tui);

  return 0;
}
