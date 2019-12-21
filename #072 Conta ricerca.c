/*
 * Jacopo Del Granchio
 * #072 18.12.2019
 *
 * Ricerca un valore in un vettore e conta quante volte è presente.
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
int ricerca(int v[], int n, int);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int n, r;
  chiedi("Inserire la lunghezza del vettore: ", "%d", &n);
  chiedi("Inserire il numero da cercare: ", "%d", &r);

  int v[n];
  carica(v, n);

  int esiste = ricerca(v, n, r);
  printf("Il valore esiste %d volte nel vettore.\n", esiste);

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

int ricerca(int v[], int n, int r) {
  int ris = 0;

  for (int i = 0; i < n; i++)
    if (v[i] == r) ris++;

  return ris;
}
