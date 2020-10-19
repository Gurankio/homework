/*
 * Jacopo Del Granchio
 * #008
 *
 * Dati i cateti di un triangolo, calcola il perimetro e l'area.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int main() {
  int a, b;

  printf("Dammi i due cateti del triangolo rettangolo: ");
  scanf("%d %d", &a, &b);

  printf("Il perimetro è %.2f\n", a + b + sqrt(pow(a, 2) + pow(b, 2)));
  printf("L'area' è %.2f\n", (a * b) / 2.0);

  system("pause");
  return 0;
}
