/*
 * Jacopo Del Granchio
 * #078 18.12.2019
 *
 * Riempie un vettore con numeri casuali crescenti.
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
void caricaCrescente(int v[], int n, int max);
bool controllaCrescente(int v[], int n);
void caricaRand(int v[], int n, int);
void scarica(int v[], int n);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int n, max;
  chiedi("Inserisci la lunghezza: ", "%d", &n);
  chiedi("Inserisci il massimo: ", "%d", &max);

  int v[n];
  caricaCrescente(v, n, max);
  scarica(v, n);

  // getchar();
  // system("pause");
  return 0;
}

void caricaCrescente(int v[], int n, int max) {
  caricaRand(v, n, max);
  int t;

  while (!controllaCrescente(v, n)) {
    for (int i = 1; i < n; i++) {
      if (v[i - 1] > v[i]) {
        t = v[i - 1];
        v[i - 1] = v[i];
        v[i] = t;
      }
    }
  }
}

bool controllaCrescente(int v[], int n) {
  bool r = 1;

  for (int i = 1; i < n; i++)
    if (v[i - 1] > v[i]) r = 0;

  return r;
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
