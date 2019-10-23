/*
 * Jacopo Del Granchio
 * #024 23.10.2019
 *
 * Calcola la media fino a che non si preme 0.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "utf-q8");

  int t, n = 0;
  float risultato = 0;

  do {
    printf("Dammi un numero (0 per uscire): ");
    scanf("%d", &t);

    risultato += t;
    n++;
  } while (t != 0);

  printf("La media è %.2f\n", risultato / (n - 1));

  // getchar();
  // system("pause");
  return 0;
}
