/*
 * Jacopo Del Granchio
 * #021 22.10.2019
 *
 * Calcola la somma di N numeri in input.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int main() {
  int n, t, risultato = 0;

  printf("Quanti numeri? ");
  scanf("%d", &n);

  for (int i = 0; i < n; ++i) {
    printf("Dammi un numero: ");
    scanf("%d", &t);

    risultato += t;
  }

  printf("Risultato: %d\n", risultato);

  // getchar();
  // system("pause");
  return 0;
}
