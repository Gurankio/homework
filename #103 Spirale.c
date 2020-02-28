/*
 * Jacopo Del Granchio
 * #103 29.02.2020
 *
 * Matrice con pattern a spirale. Ricorsivo.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>

// Costanti
#define N 7
#define M 3

// Prototipi
void spirale(int [N][M], int, int*);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int m[N][M] = {0}, count = 0;
  spirale(m, 0, &count);

  for (int i=0; i<N; i++) {
    for (int j=0; j<M; j++) {
      printf("%-*d", 5, m[i][j]);
    }
    putchar('\n');
  }

  // getchar();
  // system("pause");
  return 0;
}

void spirale(int m[N][M], int d, int *count) {
  int max = 0;
  if (N < M) {
    max = N / 2;
    max += N % 2;
  } else {
    max = M / 2;
    max += M % 2;
  }
  if (d >= max) return;

  for (int i=d; i<M-d; i++) {
    m[d][i] = (*count)++;
  }

  for (int i=1; i<N-(d*2); i++) {
    m[d+i][M-1-d] = (*count)++;
  }

  for (int i=1; i<M-(d*2); i++) {
    m[N-1-d][M-1-d-i] = (*count)++;
  }

  for (int i=1; i<N-(d*2)-1; i++) {
    m[N-1-d-i][d] = (*count)++;
  }

  spirale(m, d+1, count);
}
