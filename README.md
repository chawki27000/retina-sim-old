# Retina Sim

A Real-Time NoC simulator

## Configuration

### config.yml
The config.yml file that is located in **input** package allows to configure the NoC architecture and simulation parameters
```yaml
noc:
  dimension: 3 # NoC dimension (n*n)
  numberOfVC: 1 # number of VC per Input port
  VCBufferSize: 10 # VC buffer's size

simulation:
  period: 40 # period of simulation
```
### scenario.json
Also located in **input** package, it contains the scenario of routers communications
```json
{
  "scenario":
  [
    {
      "src": {"x": 0, "y": 0},
      "dest": {"x": 0, "y": 1},
      "message": 128, 
      "time": 0 
    },
    {
      "src": {"x": 1, "y": 1},
      "dest": {"x": 1, "y": 2},
      "message": 64,
      "time": 1
    }
  ]
}
```

## Execution

To execute the program, you must have maven installed in your machine
```
$ sudo apt-get install maven
```

And at the end, go to the root path of the project :
```
$ mvn clean compile exec:java
```

## Authors

* **Houssam Eddine ZAHAF** - [CRIStAL](https://www.cristal.univ-lille.fr) - Université de Lille
* **Chawki BENCHEHIDA** - [LAPECI Lab](http://lapeci.org/) - Université d'Oran 1
* **Mohammed Kamel BENHAOUA** - [LAPECI Lab](http://lapeci.org/) - Université d'Oran 1

## Licence
[GPL](www.gnu.org/licenses/gpl-3.0.html)