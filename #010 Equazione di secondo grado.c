/*
 * Jacopo Del Granchio
 * #010 15.10.2019
 *
 * Risolve una equazione di secondo grado.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int main() {
  float a, b, c, delta;

  printf("Dammi i coefficenti di una equazione: ");
  scanf(" %f %f %f", &a, &b, &c);

  if (a == 0 && b == 0) {
    if (c == 0) printf("Qualsiasi x\n");
    else printf("Impossibile\n");
  } else {
    if (a == 0) printf("Soluzione: %f\n", -c / b);
    else {
      delta = pow(b, 2) - 4 * a * c;

      if (delta < 0) printf("Impossibile\n");
      else {
        printf("Soluzione 1: %.2f\n", (-b + sqrt(delta)) / (2 * a));
        printf("Soluzione 2: %.2f\n", (-b - sqrt(delta)) / (2 * a));
      }
    }
  }

  getchar();
  // system("pause");
  return 0;
}
