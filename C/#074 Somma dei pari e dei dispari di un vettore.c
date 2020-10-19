/*
 * Jacopo Del Granchio
 * #074 18.12.2019
 *
 * Stampa la somma dei pari e dei dispari di un vettore.
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
void calcola(int [], int, int *, int *);

// Funzioni
int main() {
  setlocale(LC_ALL, "en_US");

  int n;
  chiedi("Inserisci la lunghezza: ", "%d", &n);

  int v[n];
  carica(v, n);

  int sommaP, sommaD;
  calcola(v, n, &sommaP, &sommaD);

  printf("Pari: %d\tDispari: %d\n", sommaP, sommaD);

  // getchar();
  // system("pause");
  return 0;
}

void calcola(int v[], int n, int *pari, int *dispari) {
  for (int i = 0; i < n; ++i) {
    if (v[i] % 2 == 0) *pari += v[i];
    else *dispari += v[i];
  }
}

void carica(int v[], int n) {
  for (int i = 0; i < n; i++) {
    printf("Inserisci il %d elemento del vettore: ", i);
    scanf("%d", &v[i]);
  }
}
