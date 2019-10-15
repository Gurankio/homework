#include <stdio.h>

#define B 5
#define C 3

int a;

int main() {
  printf("Dammi un numero: ");
  scanf(" %d", &a);

  if (a % (B * C) == 0) printf("'%d' è divisibile per '%d' e '%d'.\n", a, B, C);
  else printf("'%d' non è divisibile per '%d' e '%d'.\n", a, B, C);

  getchar();
  return 0;
}
