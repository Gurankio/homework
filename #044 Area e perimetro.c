/*
 * Jacopo Del Granchio
 * #044 26.11.2019
 *
 * Calcola area e perimetro di un rettangolo.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>
#include <stdarg.h>

void chiedi(char *, char *, ...);
float calcola(float, float, float *);
float perimetro(float, float);
float area(float, float);

int main() {
  setlocale(LC_ALL, "");

  float a, b, perimetro, area;
  chiedi("Inserisci la base: ", "%f", &a);
  chiedi("Inserisci l'altezza: ", "%f", &b);
  perimetro = calcola(a, b, &area);
  printf("Il perimetro è %.2f e l'area è %.2f\n", perimetro, area);

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

float calcola(float a, float b, float *res) {
  *res = area(a, b);
  return perimetro(a, b);
}

float perimetro(float a, float b) {
  return 2 * a + 2 * b;
}

float area(float a, float b) {
  return a * b;
}
