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

  // Conta occorrenze
  putchar('\n');

  int t, conto = 0, y[N], x[M];

  printf("Inserire il numero da cercare: ");
  scanf("%d", &t);

  for (int i = 0; i < N; i++)
    for (int j = 0; j < M; j++)
      if (matrice[i][j] == t) {
        y[conto] = i;
        x[conto] = j;
        conto++;
      }

  printf("Il valore %d compare %d volte.\n", t, conto);

  for (int i = 0; i < conto; i++)
    printf("Alla posizione con coordinate X:%d, Y:%d\n", x[i], y[i]);

  // Somma 0
  putchar('\n');

  int sum0 = 0;

  for (int i = 0; i < M; i++)
    sum0 += matrice[0][i];

  printf("La somma della riga 0 e' %d\n", sum0);

  // Somma colonna
  putchar('\n');

  int sum;

  for (int i = 0; i < M; i++) {
    sum = 0;

    for (int j = 0; j < N; j++)
      sum += matrice[j][i];

    printf("La somma della colonna %d e' %d\n", i, sum);
  }

  // somma diagonali
  putchar('\n');

  for (int j = 0; j < M; j++) {
    sum = 0;

    for (int k = 0; k < N && j + k < M; k++)
      sum += matrice[k][j + k];

    printf("La somma di una diagonale e' %d\n", sum);
  }

  /*
     for (int j = 1; j < N; j++) {
     sum = 0;

     for (int k = 0; k < M && j + k < N; k++)
      sum += matrice[j + k][k];

     printf("La somma di una diagonale e' %d\n", sum);
     }
   */
  printf("La somma di una diagonale e' %d\n", matrice[N - 1][0]);

  putchar('\n');

  for (int j = 0; j < M; j++) {
    sum = 0;

    for (int k = 0; k < N && j - k >= 0; k++)
      sum += matrice[k][j - k];

    printf("La somma di una diagonale e' %d\n", sum);
  }

  printf("La somma di una diagonale e' %d\n", matrice[N - 1][M - 1]);

  // getchar();
  // system("pause");
  return 0;
}
