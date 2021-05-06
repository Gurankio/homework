function x() {
    document.getElementById("testo").innerHTML = "Ciao!";
}

function x() {
    document.write("Ciao!");
}

function x() {
    document.write("Ciao!");
}

function x() {
    window.alert(5 + 6);
}

function x() {
    alert(5 + 6);
}

function x() {
    console.log(5 + 6);
}

function x() {
    function myFunction() {
        document.getElementById("testo1").innerHTML = "Hello World!";
        document.getElementById("testo2").innerHTML = "How are you?";
    }
}

function x() {
    var x;
    x = 6;
    document.getElementById("demo").innerHTML = x;
}

function x() {
    document.getElementById("demo").innerHTML = "Marco" + " " + "Polo";
}

function x() {
    var x = 5;
    var y = 6;
    var z = x + y;
    document.getElementById("demo").innerHTML = "Il valore di z è: " + z;
}

function x() {
    var prezzo1 = 5;
    var prezzo2 = 6;
    var totale = prezzo1 + prezzo2;
    document.getElementById("demo").innerHTML = "Il totale è: " + totale;
}

function x() {
    var pi = 3.14;
    var persona = "Marco Polo";
    var città = 'Venezia';
    document.getElementById("demo").innerHTML = pi + "<br>" + persona + "<br>" + città;
}

function x() {
    var carName;
    carName = "Volvo";
    document.getElementById("demo").innerHTML = carName;
}

function x() {
    var persona = "Marco Polo", carName = "Volvo", prezzo = 200;
    document.getElementById("demo").innerHTML = carName;
}

function x() {
    var persona = "Marco Polo", carName = "Volvo", prezzo = 200;
    document.getElementById("demo").innerHTML = carName;
}

function x() {
    var carName;
    document.getElementById("demo").innerHTML = carName;
}

function x() {
    var carName = "Volvo";
    var carName;
    document.getElementById("demo").innerHTML = carName;
}

function x() {
    var x = 5 + 2 + 3;
    document.getElementById("demo").innerHTML = x;
}

function x() {
    var x = "Marco" + " " + "Polo";
    document.getElementById("demo").innerHTML = x;
}

function x() {
    x = "5" + 2 + 3;
    document.getElementById("demo").innerHTML = x;
}

function x() {
    x = 2 + 3 + "5" + 6 + 7;
    document.getElementById("demo").innerHTML = x;
}

function x() {
    var carName = "Volvo"; /*variabile dichiarata prima             della funzione */
    myFunction();

    function myFunction() {
        document.getElementById("demo").innerHTML = "Posso visualizzare" + carName;
    }
}

function x() {
    myFunction();

    function myFunction() {
        var carName = "Volvo"; /*variabile dichiarata nello stesso blocco della          funzione */
        document.getElementById("demo1").innerHTML = typeof carName + " " + carName; /*typeof restituisce il tipo della         variabile*/
    }

    document.getElementById("demo2").innerHTML = typeof carName;
}

function x() {
    {
        {
            var x = 5;
        }
        let y = 7;
    }
    document.write(x);
    document.write(y);
}

function x() {
    var x = 10; // in questo blocco x vale 10
    {
        var x = 2;
        // in questo blocco x vale 2
        document.getElementById("prova").innerHTML = x;
    }
    // in questo blocco x vale 2
    document.getElementById("prova1").innerHTML = x;
}

    function x() {
        var x = 10; // in questo blocco x vale 10
        {         let x = 2; // in questo blocco x vale 2
                 document.getElementById("prova").innerHTML = x;
                 }       // in questo blocco x vale 10
                 document.getElementById("prova1").innerHTML = x;
            }
        function x() {
            var i = 5;
            for (var i = 0; i < 10; i++) {
                // ...
                     }
            document.getElementById("valore_x_var").innerHTML = i;
}          <hr />     <h2>Ciclo con l'utilizzo di let</h2>     <p>Il valore della variabile n al termine del ciclo è</p>     <p id="valore_x_let"></p>      function x() {       let n = 5;       for (let n = 0; n < 10; n++) {         // ...       }       document.getElementById("valore_x_let").innerHTML = n;     }
                function x() {
                    var carName = "Volvo";       // in questa parte del codice si può usare window.carName       document.getElementById("demo").innerHTML = "Casa         automobilistica " + window.carName;     }
                    function x() {
                        let carName = "Volvo";       // non è possibile utilizzare window.carName       document.getElementById("demo").innerHTML = "Casa        automobilistica" + window.carName;     }
                        function x() {
                            carName = "Volvo";
                            document.getElementById("demo").innerHTML = "Casa automobilistica" + carName;
                            var carName;
                        }

                        function x() {
                            try {
                                carName = "Volvo";
                                let carName;
                                document.getElementById("demo").innerHTML = "Casa automobilistica" + carName;
                            } catch (errore) {
                                document.getElementById("demo").innerHTML = errore.name + ": " + errore.message;
                            }
                        }

                        function x() {
                            try {
                                const PI = 3.141592653589793;
                                PI = 3.14;
                            } catch (errore) {
                                document.getElementById("demo").innerHTML = errore;
                            }
                        }

                        function x() {       // creo un oggetto:       const car = {type:"Fiat", model:"500", color:"bianca"};       // modifico una proprietà:       car.color = "rossa";       // aggiungo una proprietà:       car.owner = "Marco";       // visualizzo le proprietà:       document.getElementById("demo").innerHTML =       "Marca " + car.type + "; Modello " + car.model + ";       Colore " + car.color + "; Proprietario " + car.owner;     }
                            function x() {
                                try {
                                    const car = {type: "Fiat", model: "500", color: "bianca"};
                                    car = {type: "Volvo", model: "EX60", color: "rossa"};
                                } catch (errore) {
                                    document.getElementById("demo").innerHTML = errore;
                                }
                            }

                            function x() {       // creo un Array:       const cars = ["Saab", "Volvo", "BMW"];       // modifico il primo elemento:       cars[0] = "Toyota";       // aggiungo un elemento:       cars.push("Audi");       // visualizzo l'Array:       document.getElementById("demo").innerHTML = cars;     }
                                function x() {
                                    try {
                                        const cars = ["Saab", "Volvo", "BMW"];
                                        cars = ["Toyota", "Volvo", "Audi"];
                                    } catch (errore) {
                                        document.getElementById("demo").innerHTML = errore;
                                    }
                                }

                                function x() {
                                    carName = "Volvo";
                                    const carName;
                                    document.getElementById("demo").innerHTML = carName;
                                }

                                function x() {
                                    var x = 10;
                                    x += 5;
                                    document.getElementById("demo").innerHTML = x;
                                }

                                function x() {
                                    txt1 = "Oggi è ";
                                    txt1 += "una bella giornata";
                                    document.getElementById("demo").innerHTML = txt1;
                                }

                                Ma
                                anche: function x() {
                                    var txt1 = "Marco";
                                    var txt2 = "Polo";
                                    document.getElementById("demo").innerHTML = txt1 + " " + txt2;
                                }

                                function x() {
                                    var x = 5;
                                    var y = 5;
                                    var z = 6;
                                    document.write((x == y) + "<br />" + (x == z));
                                }

                                function x() {
                                    var persona = {nome: "Marco", cognome: "Polo", anni: 50, altezza: 175};
                                    document.write(persona.nome + " ha " + persona.anni + " anni.");
                                }

                                function x() {
                                    var x = 50
                                    document.write(typeof "Marco" + "<br />" + typeof x + "<br />" + typeof (50 ** 2));
                                }

                                function x() {
                                    var car;
                                    document.write(car + "<br />" + typeof car);
                                }

                                function x() {
                                    var car = "Volvo";
                                    car = undefined;
                                    document.write(car + "<br />" + typeof car);
                                }

                                function x() {
                                    var car = "";
                                    document.write("Il valore è: " + car + "<br />" + "Il tipo è: " + typeof car);
                                }

                                function x() {
                                    var persona = {nome: "Marco", cognome: "Polo", anni: 50, altezza: 175};
                                    persona = null;
                                    document.write(typeof persona);
                                    var x = 50;
                                    x = null;
                                    document.write("<br />" + typeof x);
                                }

                                function x() {
                                    var persona = {nome: "Marco", cognome: "Polo", anni: 50, altezza: 175};
                                    persona = null;
                                    document.write(typeof persona);
                                }

                                function x() {
                                    var persona = {nome: "Marco", cognome: "Polo", anni: 50, altezza: 175};
                                    persona = undefined;
                                    document.write(typeof persona);
                                }

                                function x() {
                                    document.write(typeof undefined + "<br />" + typeof null + "<hr />" + (null === undefined) + //ugual valore e stesso tipo   "<br>" +   (null == undefined)); }
                                        function x() {
                                            document.write(typeof "john" + "<br />" + typeof 3.14 + "<br />" + typeof true + "<br />" + typeof false + "<br />" + typeof x);
                                        }

                                    function x() {
                                        document.write(typeof {
                                            nome: 'Marco',
                                            anni: 34
                                        } + "<br />" + typeof [1, 2, 3, 4] + "<br />" + typeof null + "<br />" + typeof function myFunc() {
                                        });
                                    }

                                    function x() {
                                        function myFunction(p1, p2) {
                                            return p1 * p2;
                                        }

                                        document.getElementById("demo").innerHTML = myFunction(4, 3);
                                    }

                                    function x() {
                                        var x = myFunction(4, 3);
                                        document.write("x = " + x);

                                        function myFunction(a, b) {
                                            return a * b;
                                        }
                                    }

                                    function x() {
                                        function toCelsius(fahrenheit) {
                                            return (5 / 9) * (fahrenheit - 32);
                                        }

                                        document.write(toCelsius(86));
                                    }

                                    function x() {
                                        function toCelsius(f) {
                                            return (5 / 9) * (f - 32);
                                        }

                                        document.write(toCelsius);
                                    }

                                    function x() {
                                        myFunction();

                                        function myFunction() {
                                            var carName = "Volvo";
                                            document.getElementById("demo1").innerHTML = typeof carName + " " + carName;
                                        }

                                        document.getElementById("demo2").innerHTML = typeof carName;
                                    }