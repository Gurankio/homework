/*
 * Jacopo Del Granchio
 *
 * Riconosce se un triangolo dati i lati e': equilatero, isoscele, rettangolo o scaleno.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int main() {
  int a, b, c;

  printf("Dammi i lati in ordine crescente: ");
  scanf("%d %d %d", &a, &b, &c);

  if (a != 0 && b != 0 && c != 0 && a + b > c) {
    if (a == b && b == c) printf("%s\n", "Equilatero");
    else if (a == b || a == c || b == c) printf("%s\n", "Isoscele");
    else if (pow(a, 2) + pow(b, 2) == pow(c, 2)) printf("%s\n", "Rettangolo");
    else printf("%s\n", "Scaleno");
  } else printf("%s\n", "Non esiste.");

  // getchar();
  // system("pause");
  return 0;
}
