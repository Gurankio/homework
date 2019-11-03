/*
 * Jacopo Del Granchio
 * #031 06.11.2019
 *
 * Fa la media di piu N numeri in input.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "");

  int n, t, risultato = 0;

  printf("Quanti numeri? ");
  scanf("%d", &n);

  for (int i = 0; i < n; ++i) {
    printf("Dammi un numero: ");
    scanf("%d", &t);

    risultato += t;
  }

  printf("La media � %.2f\n", (float)risultato / n);

  // getchar();
  // system("pause");
  return 0;
}
