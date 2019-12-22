/*
 * Jacopo Del Granchio
 * #000 GG.MM.YYYY
 *
 * Lorem ipsum dolor sit amet, consectetur adipisicing elit,
 * sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>
#include <time.h>
#include <unistd.h>

#include "libraries/vts.c"
#include "libraries/sleep.c"

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
int getIndex(int, int);

void print(int[]);

int exists(int[], int, int);
void swap(int[], int, int);
int randomBoard(int[], int);

int move(int);
bool hasWon(int[]);

// Funzioni
int main() {
  setlocale(LC_ALL, "");
  vts_activateCommands();

  int board[16];
  int zeroP = randomBoard(board, 100);

  vts_cursorHide();

  do {
    vts_clear();
    vts_xy(0, 0);
    print(board);

    // Get new position
    int newP = move(zeroP);

    // Swap
    board[zeroP] = board[newP];
    zeroP = newP;
    board[zeroP] = 0;
  } while (!hasWon(board));

  vts_clear();
  vts_xy(0, 0);
  print(board);

  vts_xy(0, 5);
  vts_textBrightGreen();
  printf("Hai vinto!");
  vts_textColorReset();
  return 0;
}

//

int getIndex(int row, int column) {
  return column + (row * 4);
}

//

void print(int board[]) {
  for (int r = 0; r < 4; ++r) {
    for (int c = 0; c < 4; ++c) {
      int t = board[getIndex(r, c)];
      char d = t > 9 ? (char)(t / 10 + 0x30) : ' ';
      char u = t != 0 ? (char)(t % 10 + 0x30) : ' ';
      printf("%c%c\t", d, u);
    }

    printf("\n");
  }
}

//

int exists(int v[], int n, int x) {
  int r = 0;

  for (int i = 0; i < n; i++)
    if (v[i] == x) r += 1;

  return r != 0;
}

void swap(int board[], int a, int b) {
  int t = board[a];

  board[a] = board[b];
  board[b] = t;
}

int randomBoard(int board[], int times) {
  for (int i = 0; i < 16; ++i) board[i] = i;

  srand((unsigned)time(NULL) + rand());

  for (int i = 0; i < times; ++i) {
    int row = rand() % 4, column = rand() % 4;
    int posA = getIndex(row, column), posB;

    do {
      int step = (rand() % 2 ? 1 : -1);

      if (rand() % 2) posB = getIndex(row + step, column);
      else posB = getIndex(row, column + step);
    } while (posB < 0 || posB > 15);

    swap(board, posA, posB);
  }

  int zeroP = 0;

  for (int i = 0; i < 16; ++i)
    if (board[i] == 0) zeroP = i;

  return zeroP;
}

//

int move(int zeroP) {
  int column = zeroP % 4;
  int row = zeroP / 4;

  char a, b, c, nl;
  int found = 0;

  do {
    fflush(stdin);
    vts_xy(0, 5);
    vts_textBlack();

    scanf("%c", &a);

    vts_xy(0, 4);
    vts_textDeleteLine(1);
    vts_textBrightRed();

    if (a != 27) {
      printf("Not an arrow.\n");
      continue;
    }

    scanf("%c", &b);

    if (b != 91) {
      printf("Not an arrow.\n");
      continue;
    }

    scanf("%c", &c);

    switch (c) {
      case 65:

        // printf("Arrow UP.\n");

        if (row != 0) row--, found = 1;
        else printf("Not a valid move.\n");

        break;

      case 66:

        // printf("Arrow DOWN.\n");

        if (row != 3) row++, found = 1;
        else printf("Not a valid move.\n");

        break;

      case 67:

        // printf("Arrow RIGHT.\n");

        if (column != 3) column++, found = 1;
        else printf("Not a valid move.\n");

        break;

      case 68:

        // printf("Arrow LEFT.\n");

        if (column != 0) column--, found = 1;
        else printf("Not a valid move.\n");

        break;

      default:
        printf("Not an arrow.\n");
    }

    scanf("%c", &nl);
  } while (found == 0);

  vts_textColorReset();

  return getIndex(row, column);
}

bool hasWon(int board[]) {
  bool r = 1;

  for (int i = 0; i < 15; i++)
    if (board[i] != i + 1) r = 0;

  return r;
}
