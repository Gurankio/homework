/*
 * Jacopo Del Granchio
 * #061 11.12.2019
 *
 * Calcola i multipli di M tra 1 e N.
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
int multipli(int, int);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int n, m;
  chiedi("Inserisci il massimo: ", "%d", &n);
  chiedi("Inserisci il numero di cui trovare i multipli: ", "%d", &m);
  printf("Ci sono %d multipli di %d tra 1 e %d\n", multipli(n, m), m, n);

  // getchar();
  // system("pause");
  return 0;
}

int multipli(int n, int m) {
  int c = 0;

  for (int i = 1; i <= n; i++)
    c += (i % m == 0);

  return c;
}
