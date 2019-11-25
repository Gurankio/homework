/*
 * Jacopo Del Granchio
 * #047 26.11.2019
 *
 * Figura composta.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>

// Prototipi
void chiedi(char *, char *, ...);


// Funzioni
int main() {
  setlocale(LC_ALL, "");

  float a, b, r;
  chiedi("Inserisci la base del rettangolo: ", "%f", &a);
  chiedi("Inserisci l'altezza del rettangolo: ", "%f", &b);
  chiedi("Inserisci il raggio del cerchio: ", "%f", &r);
  printf("L'area totale è %.2f\n", a * b + 3.14 * r * r);

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
