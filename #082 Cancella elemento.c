/*
 * Jacopo Del Granchio
 * #082 11.01.2020
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
void caricaRand(int v[], int n, int max);
void scarica(int v[], int n);
void cancella(int search, int v[], int *n);
void shiftSx(int v[], int n, int start);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int n;
  chiedi("Inserisci la lunghezza: ", "%d", &n);

  int v[n];
  caricaRand(v, n, n * 10);
  scarica(v, n);

  int s;
  chiedi("Inserire il valore da cancellare: ", "%d", &s);

  cancella(s, v, &n);
  scarica(v, n);

  // getchar();
  // system("pause");
  return 0;
}

void shiftSx(int v[], int n, int start) {
  for (int i = start; i < n - 1; i++)
    v[i] = v[i + 1];
}

void cancella(int search, int v[], int *n) {
  bool found = false;

  for (int i = 0; i < *n; i++) {
    if (v[i] == search) {
      shiftSx(v, *n, i);
      found = true;
    }
  }

  if (found) {
    *n -= 1;
    v[*n] = 0;
  }
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
