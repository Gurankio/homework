/*
 * Jacopo Del Granchio
 * #070 18.12.2019
 *
 * Riempie un vettore con numeri casuali.
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

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int n, max;
  chiedi("Inserisci la lunghezza: ", "%d", &n);
  chiedi("Inserisci il numero massimo: ", "%d", &max);

  if (max < n) return 0;

  int v[n];
  caricaRand(v, n, max);
  scarica(v, n);

  // getchar();
  // system("pause");
  return 0;
}

int esiste(int v[], int n, int x) {
  int r = 0;

  for (int i = 0; i < n; i++)
    if (v[i] == x) r += 1;

  return r != 0;
}

void caricaRand(int v[], int n, int max) {
  srand((unsigned)time(NULL) + rand());

  v[0] = rand() % max;

  for (int i = 1; i < n; i++) {
    do v[i] = rand() % max;
    while (esiste(v, i - 1, v[i]));
  }
}

void scarica(int v[], int n) {
  printf("[ ");

  for (int i = 0; i < n; i++)
    printf("%d%s", v[i], i != n - 1 ? ", " : "");

  printf(" ]\n");
}
