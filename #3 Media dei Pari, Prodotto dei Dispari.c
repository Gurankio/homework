//

#include <stdio.h>

int a, b, c, d, e;
int size = 0, average = 0, product = 0;

int main() {
  printf("Dammi cinque numeri.\n");
  scanf(" %d %d %d %d %d", &a, &b, &c, &d, &e);

  if (a % 2 == 0) {
    average += a;
    size++;
  } else {
    if (product != 0) product *= a;
    else product += a;
  }

  if (b % 2 == 0) {
    average += b;
    size++;
  } else {
    if (product != 0) product *= b;
    else product += b;
  }

  if (c % 2 == 0) {
    average += c;
    size++;
  } else {
    if (product != 0) product *= c;
    else product += c;
  }

  if (d % 2 == 0) {
    average += d;
    size++;
  } else {
    if (product != 0) product *= d;
    else product += d;
  }

  if (e % 2 == 0) {
    average += e;
    size++;
  } else {
    if (product != 0) product *= e;
    else product += e;
  }

  if (average != 0) printf("Media dei pari: %d\n", average / size);
  else printf("Nessun numero pari.\n");

  if (product != 0) printf("Prodotto dei dispari: %d\n", product);
  else printf("Nessun numero dispari.\n");

  // equivalente a system("pause") ma multipiattaforma. system("pause") è solo per windows.
  // https://stackoverflow.com/questions/9386651/pause-screen-at-program-completion-in-c
  getchar();
  return 0;
}
