/*
 * Jacopo Del Granchio
 * #046 26.11.2019
 *
 * Stampa i numeri pari fino a 100.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>

// Prototipi
void chiedi(char *, char *, ...);
int pari(int);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  for (int i = 0; i <= 100; ++i)
    if (pari(i)) printf("%d\t", i);

  printf("\n");

  // getchar();
  // system("pause");
  return 0;
}

void chiedi(char *msg, char *format, ...) {
  printf(msg);
  va_list list;
  va_start(list, format);
  vscanf(format, list);
  va_end(list);
}

int pari(int n) {
  return !(n % 2);
}
