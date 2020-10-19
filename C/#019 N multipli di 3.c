/*
 * Jacopo Del Granchio
 * #019 19.10.2019
 *
 * Controlla quanti di N numeri in input sono multipli di 3.
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

    if (t % 3 == 0) risultato++;
  }

  // getchar();
  // system("pause");
  return 0;
}
