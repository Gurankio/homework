/*
 * Jacopo Del Granchio
 * #043 12.11.2019
 *
 * Trova il numero più grande e il precedente di una sequanza di N numeri in input.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>

// Prototipi
inline void input(char *, char *, ...);
int max(int, int *);

// Main
int main() {
  setlocale(LC_ALL, "");

  int n, max1, max2;
  input("Quanti numeri? ", "%d", &n);
  max1 = max(n, &max2);

  printf("Il massimo è %d e il precendente è %d\n", max1, max2);

  // getchar();
  // system("pause");
  return 0;
}

void input(char *msg, char *format, ...) {
  printf(msg);

  va_list list;
  va_start(list, format);
  vscanf(format, list);
  va_end(list);
}

int max(int n, int *store) {
  int max = -32000;

  for (int i = 0; i < n; i++) {
    int t;
    input("Inserisci un numero: ", "%d", &t);

    if (t > max) {
      *store = max;
      max = t;
    } else if (t > *store) *store = t;
  }

  return max;
}
