/*
 * Jacopo Del Granchio
 * #099 26.02.2020
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

// Costanti
#define N 4
#define M 4

// Prototipi
void espandi(int [N][M], int x, int y);

// Funzioni
int main() {
  setlocale(LC_ALL, "");
  vts_activateCommands();

  int dati[N][M];

  srand((unsigned)time(NULL) + rand());

  for (int i=0; i<N; i++) {
    for (int j=0; j<M; j++) {
      dati[i][j] = (rand() % 8) + 1;
    }
  }

  int y = rand() % N, x = rand() % M;
  espandi(dati, x, y);

  for (int i=0; i<N; i++) {
    for (int j=0; j<M; j++) {
      int value = dati[i][j];
      if (value < 0) {
        vts_foregroundCyan();
      } else {
        vts_foregroundDefault();
      }
      if (i == y && j == x) {
        vts_foregroundGreen();
      }
      printf("%-*d", 5, abs(dati[i][j]));
    }
    putchar('\n');
  }
  printf("Fonte -> Verde\n");
  printf("Espansione -> Blu\n");

  // getchar();
  // system("pause");
  return 0;
}

void espandi(int dati[N][M], int x, int y) {
  int value = dati[y][x];
  dati[y][x] *= -1;

  if (x != 0 && dati[y][x-1] >= 0 && dati[y][x-1] <= value) espandi(dati, x-1, y);
  if (x != M-1 && dati[y][x+1] >= 0 && dati[y][x+1] <= value) espandi(dati, x+1, y);

  if (y != 0 && dati[y-1][x] >= 0 && dati[y-1][x] <= value) espandi(dati, x, y-1);
  if (y != N-1 && dati[y+1][x] >= 0 && dati[y+1][x] <= value) espandi(dati, x, y+1);
}
