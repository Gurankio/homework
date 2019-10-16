#include <stdio.h>

int main() {
  int t, size = 0, average = 0, product = 0;

  for (int i = 0; i < 5; ++i) {
    printf("Dammi un numero: ");
    scanf("%d", &t);

    if (t % 2 == 0) {
      average += t;
      size++;
    } else {
      if (product != 0) product *= t;
      else product += t;
    }
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
