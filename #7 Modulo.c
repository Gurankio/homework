// Controlla se a alla b e' un numero pari o dispari senza la funzione pow()

#include <stdio.h>

int a, b;

int main() {
  printf("Dammi due numeri: ");
  scanf(" %d %d", &a, &b);

  if (b != 0) {
    if (a % 2 == 0) printf("'%d' alla '%d' e' pari.\n", a, b);
    else printf("'%d' alla '%d' e' dispari.\n", a, b);
  } else printf("'%d' alla '%d' e' dispari.\n", a, b);

  getchar();
  return 0;
}
