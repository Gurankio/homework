/*
 * Jacopo Del Granchio
 * #056 04.12.2019
 *
 * Ricerca un valore in un vettore.
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
void carica(int v[], int n);
bool ricerca(int v[], int n, int);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int n, r;
  chiedi("Inserire la lunghezza del vettore: ", "%d", &n);
  chiedi("Inserire il numero da cercare: ", "%d", &r);

  int v[n];
  carica(v, n);

  bool esiste = ricerca(v, n, r);
  printf("Il valore %sesiste nel vettore.\n", esiste ? "" : "non ");

  // getchar();
  // system("pause");
  return 0;
}

void carica(int v[], int n) {
  for (int i = 0; i < n; i++) {
    printf("Inserisci il %d elemento del vettore: ", i);
    scanf("%d", &v[i]);
  }
}

bool ricerca(int v[], int n, int r) {
  for (int i = 0; i < n; i++)
    if (v[i] == r) return true;

  return false;
}
