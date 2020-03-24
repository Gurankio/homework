/*
 * Menu ad Albero da file.
 */

#ifndef MENU
#define MENU

#include <stdlib.h>
#include <stdarg.h>
#include <stdio.h>
#include <string.h>

typedef struct {
  char *text;
  void (*handler)(void *[]);
  void **params;
} choice;

typedef struct {
  // Header
  char **titles;
  int titleCount;

  // Body
  choice *choices;
  int choiceCount;
} menu;

typedef struct {
  menu *menus;
  int *menuIndexes;
  int menuCount;
} menuCollection;

const int MAX_LENGHT = 128;

// Macros
#define PP_NARG(...) \
  PP_NARG_(__VA_ARGS__, PP_RSEQ_N())
#define PP_NARG_(...) \
  PP_ARG_N(__VA_ARGS__)
#define PP_ARG_N( \
    _1, _2, _3, _4, _5, _6, _7, _8, _9, _10, \
    _11, _12, _13, _14, _15, _16, _17, _18, _19, _20, \
    _21, _22, _23, _24, _25, _26, _27, _28, _29, _30, \
    _31, _32, _33, _34, _35, _36, _37, _38, _39, _40, \
    _41, _42, _43, _44, _45, _46, _47, _48, _49, _50, \
    _51, _52, _53, _54, _55, _56, _57, _58, _59, _60, \
    _61, _62, _63, _64, _65, _66, _67, _68, _69, _70, \
    _71, _72, _73, _74, _75, _76, _77, _78, _79, _80, \
    _81, _82, _83, _84, _85, _86, _87, _88, _89, _90, \
    _91, _92, _93, _94, _95, _96, _97, _98, _99, _100, \
    _101, _102, _103, _104, _105, _106, _107, _108, _109, _110, \
    _111, _112, _113, _114, _115, _116, _117, _118, _119, _120, \
    _121, _122, _123, _124, _125, _126, _127, N, ...) N
#define PP_RSEQ_N() \
  127, 126, 125, 124, 123, 122, 121, 120, \
  119, 118, 117, 116, 115, 114, 113, 112, 111, 110, \
  109, 108, 107, 106, 105, 104, 103, 102, 101, 100, \
  99, 98, 97, 96, 95, 94, 93, 92, 91, 90, \
  89, 88, 87, 86, 85, 84, 83, 82, 81, 80, \
  79, 78, 77, 76, 75, 74, 73, 72, 71, 70, \
  69, 68, 67, 66, 65, 64, 63, 62, 61, 60, \
  59, 58, 57, 56, 55, 54, 53, 52, 51, 50, \
  49, 48, 47, 46, 45, 44, 43, 42, 41, 40, \
  39, 38, 37, 36, 35, 34, 33, 32, 31, 30, \
  29, 28, 27, 26, 25, 24, 23, 22, 21, 20, \
  19, 18, 17, 16, 15, 14, 13, 12, 11, 10, \
  9, 8, 7, 6, 5, 4, 3, 2, 1, 0

void nullHandler(void *params[]);
static void _bindFunction(choice *choice, void (*handler)(void *[]), int paramCount, ...);
#define bindFunction(choice, handler, ...) _bindFunction(choice, handler, PP_NARG(__VA_ARGS__), __VA_ARGS__)
int loadMenu(char *path, menuCollection *menuCollection);
void freeMenu(menuCollection *menuCollection);
void render(menu *menu);

menu * getMenu(menuCollection *menuCollection, int menuAddress);
choice * getChoice(menuCollection *menuCollection, int menuAddress, int choiceAddress);

// NOP function for initialization.
void nullHandler(void *params[]) {
  return;
}

// TODO: Should check mallocs
void _bindFunction(choice *choice, void (*handler)(void *[]), int paramCount, ...) {
  choice->handler = handler;
  choice->params = malloc(paramCount * sizeof(void *));

  va_list list;
  va_start(list, paramCount);

  for (int i = 0; i < paramCount; i++) choice->params[i] = va_arg(list, void *);

  va_end(list);
}

// TODO: Should check mallocs
int loadMenu(char *path, menuCollection *menuCollection) {
  FILE *file = fopen(path, "r");

  if (file == NULL) {
    printf("Path error.\n");
    return 0;
  }

  int tempMenu, tempTitle, tempChoice;

  int totalMenu = 0;
  char buffer[MAX_LENGHT];

  while (fgets(buffer, MAX_LENGHT, file) != NULL)
    if (sscanf(buffer, "#%d)%d,%d", &tempMenu, &tempTitle, &tempChoice) == 3) totalMenu++;

  menuCollection->menuCount = totalMenu;
  menuCollection->menuIndexes = malloc(totalMenu * sizeof(int));
  menuCollection->menus = malloc(totalMenu * sizeof(menu));

  if (fseek(file, 0, SEEK_SET) != 0) {
    printf("Error while rewinding file.\n");
    return 0;
  }

  int i = 0;

  while (fgets(buffer, MAX_LENGHT, file) != NULL) {
    if (sscanf(buffer, "#%d)%d,%d", &tempMenu, &tempTitle, &tempChoice) == 3) {
      menuCollection->menuIndexes[i] = tempMenu;
      menu *menu = &menuCollection->menus[i];

      menu->titleCount = tempTitle;
      menu->titles = (char **)malloc(tempTitle * sizeof(char *));

      for (int j = 0; j < tempTitle; j++) {
        if (fgets(buffer, MAX_LENGHT, file) == NULL) strcpy(buffer, "Error while reading.");

        menu->titles[j] = malloc(sizeof(buffer) * sizeof(char));
        strcpy(menu->titles[j], buffer);
      }

      menu->choiceCount = tempChoice;
      menu->choices = (choice *)malloc(tempChoice * sizeof(choice));

      for (int j = 0; j < tempChoice; j++) {
        if (fgets(buffer, MAX_LENGHT, file) == NULL) strcpy(buffer, "Error while reading.");

        menu->choices[j].text = malloc(sizeof(buffer) * sizeof(char));
        strcpy(menu->choices[j].text, buffer);
        bindFunction(&menu->choices[j], nullHandler, 0);
      }

      i++;
    }
  }

  fclose(file);
  return 1;
}

void freeMenu(menuCollection *menuCollection) {
  for (int i = 0; i < menuCollection->menuCount; i++) {
    menu *menu = &menuCollection->menus[i];

    for (int j = 0; j < menu->choiceCount; j++) {
      free(menu->choices[j].text);
      free(menu->choices[j].params);
    }

    free(menu->choices);

    for (int j = 0; j < menu->titleCount; j++) free(menu->titles[j]);

    free(menu->titles);
  }

  free(menuCollection->menuIndexes);
  free(menuCollection->menus);
}

void render(menu *menu) {
  int s;

  while (1) {
    for (int i = 0; i < menu->titleCount; i++)
      printf("   %s", menu->titles[i]);

    for (int i = 0; i < menu->choiceCount; i++)
      printf("%d) %s", i + 1, menu->choices[i].text);

    printf("0) Exit\n");

    printf("Input: ");
    scanf("%d", &s);
    putchar('\n'); // Spacing

    if (s < 0 || s > menu->choiceCount) {
      printf("\tInvalid input.\n\n");
      continue;
    }

    if (s == 0) break;

    choice *choice = &menu->choices[s - 1];
    choice->handler(choice->params);

    putchar('\n'); // Spacing
  }
}

menu * getMenu(menuCollection *menuCollection, int menuAddress) {
  if (menuAddress < 0 || menuAddress >= menuCollection->menuCount) {
    fprintf(stderr, "Invalid menu address: (%d)\n", menuAddress);
    menuAddress = 0;
  }
  return &menuCollection->menus[menuAddress];
}

choice * getChoice(menuCollection *menuCollection, int menuAddress, int choiceAddress) {
  if (choiceAddress < 0 || choiceAddress >= getMenu(menuCollection, menuAddress)->choiceCount) {
    fprintf(stderr, "Invalid choice address: (%d)\n", choiceAddress);
    choiceAddress = 0;
  }

  return &getMenu(menuCollection, menuAddress)->choices[choiceAddress];
}

#endif

// void qwe(void *arg[]) {
//   int *as = (int *)arg[0];
//   int as2 = (long int)arg[1];
//
//   printf("\t\t%d, %d\n", *as, as2);
//   render(getMenu(arg[2], 2));
// }
//
// int main() {
//   int sa = 12;
//   menuCollection menuCollection;
//
//   if (!loadMenu("../data/MENUTEST/testB.md", &menuCollection)) exit(1);
//
//   bindFunction(getChoice(&menuCollection, 0, 1), qwe, &sa, 213, &menuCollection);
//   render(getMenu(&menuCollection, 0)); // Blocca esecuzione del main.
//   freeMenu(&menuCollection);
//   return 0;
// }
