#include <stdio.h>

int a;

int main() {
  printf("Dammi un numero.\n");
  scanf(" %d", &a);
  printf("Il numero e' %s.\n", a % 2 == 0 ? "pari" : "dispari");

  getchar();
  return 0;
}
