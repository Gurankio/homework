/*
 * Jacopo Del Granchio
 * #066 18.12.2019
 *
 * Dato un vettore lo divide in due vettori,
 * uno con i pari, uno con i dispari.
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
void azzera(int v[], int n);
void carica(int v[], int n);
void scarica(int v[], int n);
void calcola(int[], int, int [], int *, int[], int *);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int n;
  chiedi("Inserisci la lunghezza: ", "%d", &n);

  int v[n];
  carica(v, n);

  int p[n], pI = 0, d[n], dI = 0;

  calcola(v, n, p, &pI, d, &dI);

  scarica(p, pI);
  scarica(d, dI);

  // getchar();
  // system("pause");
  return 0;
}

void calcola(int v[], int n, int p[], int *pI, int d[], int *dI) {
  for (int i = 0; i < n; ++i) {
    if (v[i] % 2 == 0) p[(*pI)++] = v[i];
    else d[(*dI)++] = v[i];
  }
}

void azzera(int v[], int n) {
  for (int i = 0; i < n; i++) v[i] = 0;
}

void carica(int v[], int n) {
  for (int i = 0; i < n; i++) {
    printf("Inserisci il %d elemento del vettore: ", i);
    scanf("%d", &v[i]);
  }
}

void scarica(int v[], int n) {
  printf("[ ");

  for (int i = 0; i < n; i++)
    printf("%d%s", v[i], i != n - 1 ? ", " : "");

  printf(" ]\n");
}
