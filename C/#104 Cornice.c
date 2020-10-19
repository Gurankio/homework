/*
 * Jacopo Del Granchio
 * #104 04.03.2020
 *
 * Matrice con pattern a cornice. Ricorsivo.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>

#include "libraries/sleep.c"

// Costanti
#define N 15
#define M 15

// Prototipi
void cornice(int [N][M], int, int*);
void stampa(int [N][M]);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int m[N][M], count = 1;

  for (int i=0; i<N; i++) {
    for (int j=0; j<M; j++) {
      m[i][j] = -1;
    }
  }

  cornice(m, 0, &count);

  /*

  for (int i=0; i<N; i++) {
    for (int j=0; j<M; j++) {
      char buffer[10];
      sprintf(buffer, "%d", m[i][j]);
      printf("%-*s", 5, m[i][j] != -1 ? buffer : "");
    }
    putchar('\n');
  }

  */

  // getchar();
  // system("pause");
  return 0;
}

// BUG con altezza (N = 5, M = 3) dispari, non solo.
void cornice(int m[N][M], int d, int *count) {
  if (d >= (N < M ? N / 2 + N % 2 : M / 2 + M % 2)) return;

  for (int i=d; i<M-d; i++) {
    m[d][i] = (*count);
    stampa(m);
  }

  for (int i=1; i<N-(d*2); i++) {
    m[d+i][M-1-d] = (*count);
    stampa(m);
  }

  for (int i=1; i<M-(d*2); i++) { // BUG con altezza (N) dispari, non solo.
    m[N-1-d][M-1-d-i] = (*count);
    stampa(m);
  }

  for (int i=1; i<N-(d*2)-1; i++) {
    m[N-1-d-i][d] = (*count);
    stampa(m);
  }

  (*count)++;
  cornice(m, d+1, count);
}

void stampa(int m[N][M]) {
  system("clear");
  for (int i=0; i<N; i++) {
    for (int j=0; j<M; j++) {
      char buffer[10];
      sprintf(buffer, "%d", m[i][j]);
      printf("%-*s", 5, m[i][j] != -1 ? buffer : "");
    }
    putchar('\n');
  }
  cp_sleep(50);
}
