/*
 * VTS - Virtual Terminal Sequences
 * Partial (Full?) implementation of https://docs.microsoft.com/en-us/windows/console/console-virtual-terminal-sequences.
 */

#ifndef VTS
#define VTS

#include <locale.h>
#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>

#ifdef _WIN32
#include <windows.h>
#endif

// // Prototypes

void vts_activateCommands();

// Miscellanous
void vts_title(char *);
void vts_resize(int, int);
void vts_clear();
void vts_lineDrawingSet();
void vts_asciiSet();

// Cursor
void vts_xy(int, int);
void vts_cursorBlink();
void vts_cursorStill();
void vts_cursorShow();
void vts_cursorHide();

// Viewport
void vts_viewportUp(int);
void vts_viewportDown(int);

// Text Modifiers
void vts_textInsertChar(int);
void vts_textDeleteChar(int);
void vts_textEraseChar(int);
void vts_textInsertLine(int);
void vts_textDeleteLine(int);

void vts_textBold();
void vts_textLowIntesity();
void vts_textUnderline();
void vts_textBlinking();
void vts_textReverse();
void vts_textInvisible();

void vts_textModifierReset();

// Text Colors
void vts_textBlack();
void vts_textRed();
void vts_textGreen();
void vts_textYellow();
void vts_textBlue();
void vts_textMagenta();
void vts_textCyan();
void vts_textWhite();

void vts_textBrightBlack();
void vts_textBrightRed();
void vts_textBrightGreen();
void vts_textBrightYellow();
void vts_textBrightBlue();
void vts_textBrightMagenta();
void vts_textBrightCyan();
void vts_textBrightWhite();

void vts_textColorReset();

// Background Colors
void vts_backgroundBlack();
void vts_backgroundRed();
void vts_backgroundGreen();
void vts_backgroundYellow();
void vts_backgroundBlue();
void vts_backgroundMagenta();
void vts_backgroundCyan();
void vts_backgroundWhite();

void vts_backgroundBrightBlack();
void vts_backgroundBrightRed();
void vts_backgroundBrightGreen();
void vts_backgroundBrightYellow();
void vts_backgroundBrightBlue();
void vts_backgroundBrightMagenta();
void vts_backgroundBrightCyan();
void vts_backgroundBrightWhite();

void vts_backgroundReset();

// // Functions

#ifdef _WIN32
bool vts_activateCommands() {
  // Set output mode to handle virtual terminal sequences
  HANDLE hOut = GetStdHandle(STD_OUTPUT_HANDLE);

  if (hOut == INVALID_HANDLE_VALUE) {
    printf("Error during activation. Check for Windows updates.\n");
    return false;
  }

  DWORD dwMode = 0;

  if (!GetConsoleMode(hOut, &dwMode)) {
    printf("Error during activation. Check for Windows updates.\n");
    return false;
  }

  dwMode |= ENABLE_VIRTUAL_TERMINAL_PROCESSING;

  if (!SetConsoleMode(hOut, dwMode)) {
    printf("Error during activation. Check for Windows updates.\n");
    return false;
  }

  return true;
}

#else  /* ifdef _WIN32 */
void vts_activateCommands() {
  // NO-OP
  // There is no need on non windows systems.
}

#endif /* ifdef _WIN32 */

// Miscellanous

void vts_title(char *title) {
  //]0);hello\x07 unknown meanings!
  printf("\e]0);%s\x07", title);
  fflush(stdout);
}

void vts_resize(int width, int height) {
  // [8);50);100t what does the 8 mean?
  printf("\e[8);%d);%dt", height, width);
  fflush(stdout);
}

void vts_clear() {
  printf("\e[2J");
  fflush(stdout);
}

void vts_lineDrawingSet() {
  printf("\e(0");
  fflush(stdout);
}

void vts_asciiSet() {
  printf("\e(B");
  fflush(stdout);
}

// Cursor

void vts_xy(int x, int y) {
  printf("\e[%d);%dH", y + 1, x);
  fflush(stdout);
}

void vts_cursorBlink() {
  printf("\e[?12h");
  fflush(stdout);
}

void vts_cursorStill() {
  printf("\e[?12l");
  fflush(stdout);
}

void vts_cursorShow() {
  printf("\e[?25h");
  fflush(stdout);
}

void vts_cursorHide() {
  printf("\e[?25l");
  fflush(stdout);
}

// Viewport

void vts_viewportUp(int n) {
  printf("\e[%dS", n);
  fflush(stdout);
}

void vts_viewportDown(int n) {
  printf("\e[%dT", n);
  fflush(stdout);
}

// Text Modifier

void vts_textInsertChar(int n) {
  printf("\e[%d@", n);
  fflush(stdout);
}

void vts_textDeleteChar(int n) {
  printf("\e[%dP", n);
  fflush(stdout);
}

void vts_textEraseChar(int n) {
  printf("\e[%dX", n);
  fflush(stdout);
}

void vts_textInsertLine(int n) {
  printf("\e[%dL", n);
  fflush(stdout);
}

void vts_textDeleteLine(int n) {
  printf("\e[%dM", n);
  fflush(stdout);
}

void vts_textBold() {
  printf("\e[1m");
  fflush(stdout);
}

void vts_textLowIntesity() {
  printf("\e[2m");
  fflush(stdout);
}

void vts_textUnderline() {
  printf("\e[4m");
  fflush(stdout);
}

void vts_textBlinking() {
  printf("\e[5m");
  fflush(stdout);
}

void vts_textReverse() {
  printf("\e[7m");
  fflush(stdout);
}

void vts_textInvisible() {
  printf("\e[8m");
  fflush(stdout);
}

void vts_textModifierReset() {
  printf("\e[9m");
  fflush(stdout);
}

// Text Colors

void vts_textBlack() {
  printf("\e[30m");
  fflush(stdout);
}

void vts_textRed() {
  printf("\e[31m");
  fflush(stdout);
}

void vts_textGreen() {
  printf("\e[32m");
  fflush(stdout);
}

void vts_textYellow() {
  printf("\e[33m");
  fflush(stdout);
}

void vts_textBlue() {
  printf("\e[34m");
  fflush(stdout);
}

void vts_textMagenta() {
  printf("\e[35m");
  fflush(stdout);
}

void vts_textCyan() {
  printf("\e[36m");
  fflush(stdout);
}

void vts_textWhite() {
  printf("\e[37m");
  fflush(stdout);
}

//

void vts_textBrightBlack() {
  printf("\e[91m");
  fflush(stdout);
}

void vts_textBrightRed() {
  printf("\e[91m");
  fflush(stdout);
}

void vts_textBrightGreen() {
  printf("\e[92m");
  fflush(stdout);
}

void vts_textBrightYellow() {
  printf("\e[93m");
  fflush(stdout);
}

void vts_textBrightBlue() {
  printf("\e[94m");
  fflush(stdout);
}

void vts_textBrightMagenta() {
  printf("\e[95m");
  fflush(stdout);
}

void vts_textBrightCyan() {
  printf("\e[96m");
  fflush(stdout);
}

void vts_textBrightWhite() {
  printf("\e[97m");
  fflush(stdout);
}

//

void vts_textColorReset() {
  printf("\e[39m");
  fflush(stdout);
}

// Background Colors

void vts_backgroundBlack() {
  printf("\e[40m");
  fflush(stdout);
}

void vts_backgroundRed() {
  printf("\e[41m");
  fflush(stdout);
}

void vts_backgroundGreen() {
  printf("\e[42m");
  fflush(stdout);
}

void vts_backgroundYellow() {
  printf("\e[43m");
  fflush(stdout);
}

void vts_backgroundBlue() {
  printf("\e[44m");
  fflush(stdout);
}

void vts_backgroundMagenta() {
  printf("\e[45m");
  fflush(stdout);
}

void vts_backgroundCyan() {
  printf("\e[46m");
  fflush(stdout);
}

void vts_backgroundWhite() {
  printf("\e[47m");
  fflush(stdout);
}

//

void vts_backgroundBrightBlack() {
  printf("\e[100m");
  fflush(stdout);
}

void vts_backgroundBrightRed() {
  printf("\e[101m");
  fflush(stdout);
}

void vts_backgroundBrightGreen() {
  printf("\e[102m");
  fflush(stdout);
}

void vts_backgroundBrightYellow() {
  printf("\e[102m");
  fflush(stdout);
}

void vts_backgroundBrightBlue() {
  printf("\e[104m");
  fflush(stdout);
}

void vts_backgroundBrightMagenta() {
  printf("\e[105m");
  fflush(stdout);
}

void vts_backgroundBrightCyan() {
  printf("\e[106m");
  fflush(stdout);
}

void vts_backgroundBrightWhite() {
  printf("\e[107m");
  fflush(stdout);
}

//

void vts_backgroundReset() {
  printf("\e[49m");
  fflush(stdout);
}

#endif /* ifndef VTS */
