/*
 * Jacopo Del Granchio
 * #077 18.12.2019
 *
 * Copia i pari in testa e i dispari in coda di un vettore in altro vettore.
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
void calcola(int v[], int n, int out[]);

// Funzioni
int main() {
  setlocale(LC_ALL, "en_US");

  int n;
  chiedi("Inserisci la lunghezza: ", "%d", &n);

  int v[n];
  caricaRand(v, n, n);
  scarica(v, n);

  int out[n];
  calcola(v, n, out);
  scarica(out, n);

  // getchar();
  // system("pause");
  return 0;
}

void calcola(int v[], int n, int out[]) {
  int p = 0, d = n - 1;

  for (int i = 0; i < n; ++i) {
    if (v[i] % 2 == 0) out[p++] = v[i];
    else out[d--] = v[i];
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
