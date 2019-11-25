/*
 * Jacopo Del Granchio
 * #048 26.11.2019
 *
 * Calcola il mcm e il MCD tra due numeri.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>

// Prototipi
void chiedi(char *, char *, ...);
int mcm(int, int);
int MCD(int, int);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  int a, b;
  chiedi("Inserisci il primo numero: ", "%d", &a);
  chiedi("Inserisci il secondo numero: ", "%d", &b);
  printf("Il mcm è %d e il MCD è %d\n", mcm(a, b), MCD(a, b));

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

int mcm(int a, int b) {
  int min = a < b ? a : b, max = a < b ? b : a;
  int risultato = 1, i = 1;

  do risultato = min * i++;
  while (risultato % max != 0);
  return risultato;
}

int MCD(int a, int b) {
  int min = a < b ? a : b, max = a < b ? b : a;
  int risultato = max;

  do risultato--;
  while (min % risultato != 0 || max % risultato != 0);

  return risultato;
}
