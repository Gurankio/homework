/*
 * Jacopo Del Granchio
 * #041 12.11.2019
 *
 * Somma numeri tra 10 e 100 finchè la loro somma è minore di N.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "");

  int n, t, s = 0;
  printf("Dammi il massimo: ");
  scanf("%d", &n);

  do {
    printf("Dammi un numero: ");
    scanf("%d", &t);

    if (t >= 10 && t <= 100) s += t;
  } while (s < n);

  printf("La somma è %d\n", s);

  // getchar();
  // system("pause");
  return 0;
}
