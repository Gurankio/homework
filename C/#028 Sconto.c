/*
 * Jacopo Del Granchio
 * #028 06.11.2019
 *
 * Calcola lo sconto a un prezzo in input.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "");

  float n = 1;
  const float C1 = 0.05, C2 = 0.10, C3 = 0.20;

  do {
    if (n <= 0) printf("Il costo deve essere positivo.\n\n");

    printf("Quanto costa? ");
    scanf("%f", &n);
  } while (n <= 0);

  if (n < 100) printf("Il prezzo scontato � %.2f\n", n - (n * C1));
  else if (n < 300) printf("Il prezzo scontato � %.2f\n", n - (n * C2));
  else printf("Il prezzo scontato � %.2f\n", n - (n * C3));

  // getchar();
  // system("pause");
  return 0;
}
