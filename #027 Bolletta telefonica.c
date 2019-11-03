/*
 * Jacopo Del Granchio
 * #027 06.11.2019
 *
 * Calcola il costo della bolletta telefonica,
 * dati in input il numero degli scatti.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "");

  int n = 1;
  const int A = 30, B = 100;
  float r = 2.50;
  const float C1 = 0.20, C2 = 0.15, C3 = 0.10;

  do {
    if (n < 0) printf("Gli scatti devono essere positivi.\n\n");

    printf("Quanti scatti? ");
    scanf("%d", &n);
  } while (n < 0);

  r += C1 * (n < A ? n : A);

  if (n > 30) r += C2 * (n < B ? n - A : B);

  if (n > 100) r += C3 * (n - B);

  printf("Il costo totale � %.2f\n", r);

  // getchar();
  // system("pause");
  return 0;
}
