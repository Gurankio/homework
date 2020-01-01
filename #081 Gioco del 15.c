/*
 * Jacopo Del Granchio
 * #081 07.01.2019
 *
 * Gioco del 15.
 * Esistono delle partite non risolvibili.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>
#include <time.h>

#include "libraries/vts.c"

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
int menu();
void newGame(int);
int getIndex(int, int);
void printBoard(int[]);
int exists(int[], int, int);
void swap(int[], int, int);
int randomBoard(int[], int);
int move(int);
bool hasWon(int[]);

// Funzioni
int main() {
  setlocale(LC_ALL, "");
  vts_activateCommands();

  int s, diff = 50;

  do {
    s = menu();

    switch (s) {
      case 1:
        chiedi("Inserisci la nuova difficoltà: ", "%d", &diff);
        break;

      case 2:
        newGame(diff);
        break;

      case 3:
        printf("Arrivederci.\n");
        break;

      default:
        printf("Scelta non valida.\n");
        break;
    }
  } while (s != 3);

  newGame(10);

  return 0;
}

int menu() {
  printf("1) Difficoltà\n");
  printf("2) Nuovo Gioco\n");
  printf("3) Esci\n");
  int s;
  chiedi("Scelta: ", "%d", &s);

  char nl;
  scanf("%c", &nl);

  return s;
}

void newGame(int difficulty) {
  int board[16];
  int zeroP = randomBoard(board, difficulty);

  vts_cursorHide();

  do {
    vts_clear();
    vts_xy(0, 0);
    printBoard(board);

    // Get new position
    int newP = move(zeroP);

    // Swap
    board[zeroP] = board[newP];
    zeroP = newP;
    board[zeroP] = 0;
  } while (!hasWon(board));

  vts_clear();
  vts_xy(0, 0);
  printBoard(board);

  vts_xy(0, 5);
  vts_textBrightGreen();
  printf("Hai vinto!\n");
  vts_textColorReset();
}

//

int getIndex(int row, int column) {
  return column + (row * 4);
}

//

void printBoard(int board[]) {
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
  for (int i = 0; i < 16; ++i) board[i] = (i+1) % 16;

  srand((unsigned)time(NULL) + rand());

  for (int i = 0; i < times; ++i) {
    int row = rand() % 4, column = rand() % 4;
    int posA = getIndex(row, column), newRow, newColumn;

    do {
      newRow = row, newColumn = column;
      int step = (rand() % 2 ? 1 : -1);

      if (rand() % 2) newRow += step;
      else newColumn += step;
    } while (newRow < 0 || newRow > 3 || newColumn < 0 || newColumn > 3);

    swap(board, posA, getIndex(newRow, newColumn));
  }

  int zeroP = 0;

  for (int i = 0; i < 16; ++i)
    if (board[i] == 0) zeroP = i;

  return zeroP;
}

//

int move(int zeroP) {
  int row = zeroP / 4, column = zeroP % 4;
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
