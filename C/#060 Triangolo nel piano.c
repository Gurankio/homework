/*
 * Jacopo Del Granchio
 * #060 11.12.2019
 *
 * Calcola il perimetro di un triangolo nel piano.
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
float dist(float, float, float, float);

// Funzioni
int main() {
  setlocale(LC_ALL, "");

  float xA, yA, xB, yB, xC, yC;
  chiedi("Insesrisci le coordnate del primo punto: ", "%f %f", &xA, &yA);
  chiedi("Insesrisci le coordnate del secondo punto: ", "%f %f", &xB, &yB);
  chiedi("Insesrisci le coordnate del terzo punto: ", "%f %f", &xC, &yC);

  float perimetro = 0.0;
  perimetro += dist(xA, yA, xB, yB);
  perimetro += dist(xB, yB, xC, yC);
  perimetro += dist(xC, yC, xA, yA);

  printf("Il perimetro è %.2f\n", perimetro);

  // getchar();
  // system("pause");
  return 0;
}

float dist(float xA, float yA, float xB, float yB) {
  return sqrt(pow(xB - xA, 2) + pow(yB - yA, 2));
}
