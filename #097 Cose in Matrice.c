/*
 * Jacopo Del Granchio
 * #097 19.02.2020
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi


// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int N, M;
  printf("Inserire N: ");
  scanf("%d", &N);

  printf("Inserire M: ");
  scanf("%d", &M);

  int matrice[N][M];

  // Inserire
  for (int i = 0; i < N; i++)
    for (int j = 0; j < M; j++) {
      printf("Valore [%d][%d]: ", i, j);
      scanf("%d", &matrice[i][j]);
    }

  // Stamnpa
  for (int i = 0; i < N; i++) {
    for (int j = 0; j < M; j++)
      printf("%d  ", matrice[i][j]);

    putchar('\n');
  }

  // Max
  int max = matrice[0][0], maxPosX = 0, maxPosY = 0;

  for (int i = 0; i < N; i++)
    for (int j = 0; j < M; j++)
      if (matrice[i][j] > max) {
        max = matrice[i][j];
        maxPosY = i, maxPosX = j;
      }

  printf("Il massimo e' %d ed e' alle coordinate [%d][%d]\n", max, maxPosY, maxPosX);

  // Somma 0
  int sum = 0;

  for (int i = 0; i < M; i++)
    sum += matrice[0][i];

  printf("La somma e' %d\n", sum);

  // getchar();
  // system("pause");
  return 0;
}
