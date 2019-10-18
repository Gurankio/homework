/*
 * Jacopo Del Granchio
 * #017 19.10.2019
 *
 * Stampa i numeri pari e minori di N fino a 0.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int main() {
  int n;

  printf("Dammi un numero: ");
  scanf("%d", &n);

  for (int i = n - (n % 2) - !(n % 2) * 2; i >= 0; i -= 2) {
    printf("%d\t", i);

    if (i % 20 == 0) printf("\n");
  }

  printf("\nFatto.\n");

  // getchar();
  // system("pause");
  return 0;
}
