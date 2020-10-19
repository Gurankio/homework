/*
 * Jacopo Del Granchio
 * #042 12.11.2019
 *
 * Trova il numero più piccolo e il penultimo di una sequanza di N numeri in input.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>

#define input(msg, format, ...) \
  printf(msg); \
  scanf(format, __VA_ARGS__);

int main() {
  setlocale(LC_ALL, "");

  int n, t, minore = 32767, secondo = 32767;
  input("Quanti numeri?\n", "%d", &n);

  for (int i = 0; i < n; i++) {
    input("Dammi un numero: ", "%d", &t);

    if (t < minore) {
      secondo = minore;
      minore = t;
    } else if (t < secondo) secondo = t;
  }

  printf("Il più piccolo è %d e il penultimo è %d\n", minore, secondo);

  // getchar();
  // system("pause");
  return 0;
}
