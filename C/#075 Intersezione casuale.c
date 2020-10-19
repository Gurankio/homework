/*
 * Jacopo Del Granchio
 * #075 18.12.2019
 *
 * Calcola l'intersezione di due vettori caricati in modo casuale.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>
#include <stdbool.h>
#include <time.h>

// Macro
#define chiedi(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

// Prototipi
void caricaRand(int v[], int n, int);
void scarica(int v[], int n);
void calcola(int a[], int b[], int n, int c[], int *iC);

// Funzioni
int main() {
  setlocale(LC_ALL, "en_US");

  int n;
  chiedi("Inserisci la lunghezza: ", "%d", &n);

  int a[n], b[n];
  caricaRand(a, n, 10);
  caricaRand(b, n, 10);
  scarica(a, n);
  scarica(b, n);

  int c[n], iC = 0;
  calcola(a, b, n, c, &iC);
  scarica(c, iC);

  // getchar();
  // system("pause");
  return 0;
}

void calcola(int a[], int b[], int n, int c[], int *iC) {
  for (int i = 0; i < n; ++i)
    if (a[i] == b[i]) c[(*iC)++] = a[i];
}

void caricaRand(int v[], int n, int max) {
  srand((unsigned)time(NULL) + rand());

  for (int i = 0; i < n; i++)
    v[i] = rand() % max;
}

void scarica(int v[], int n) {
  printf("[ ");

  for (int i = 0; i < n; i++)
    printf("%d%s", v[i], i != n - 1 ? ", " : "");

  printf(" ]\n");
}
