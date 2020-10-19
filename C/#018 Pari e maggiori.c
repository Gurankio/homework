/*
 * Jacopo Del Granchio
 * #018 19.10.2019
 *
 * Stampa i primi 10 numeri pari e maggiori di N.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int main() {
  int n;

  printf("Dammi un numero: ");
  scanf("%d", &n);

  for (int i = n + (n % 2) + !(n % 2) * 2; i <= n - (n % 2) + 20; i += 2)
    printf("%d\t", i);

  printf("\nFatto.\n");

  // getchar();
  // system("pause");
  return 0;
}
