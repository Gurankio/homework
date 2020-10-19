/*
 * Jacopo Del Granchio
 * #069 18.12.2019
 *
 * Dato un vettore calcola il minimo e il massimo e le loro posizione.
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
void calcola(int [], int, int *, int *, int *, int *);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int n;
  chiedi("Inserisci la lunghezza: ", "%d", &n);

  int v[n];
  carica(v, n);

  int max, maxI, min, minI;

  calcola(v, n, &max, &maxI, &min, &minI);
  printf("Massimo: %d nella posizione %d\n", max, maxI);
  printf("Minimo: %d nella posizione %d\n", min, minI);

  // getchar();
  // system("pause");
  return 0;
}

void calcola(int v[], int n, int *max, int *maxI, int *min, int *minI) {
  *max = v[0], *maxI = 0, *min = v[0], *minI = 0;

  for (int i = 1; i < n; ++i) {
    if (v[i] > *max) *max = v[i], *maxI = i;

    if (v[i] < *min) *min = v[i], *minI = i;
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
