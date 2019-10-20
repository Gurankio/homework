/*
 * Jacopo Del Granchio
 * #022 22.10.2019
 *
 * Calcola il massimo di N numeri in input.
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

    if (t > risultato) risultato = t;
  }

  printf("Risultato: %d\n", risultato);

  // getchar();
  // system("pause");
  return 0;
}
