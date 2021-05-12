package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cuenta {

  private double saldo = 0;
  private List<Movimiento> movimientos = new ArrayList<>();
  private double limite = 1000;
  private int limiteDepositos = 3;

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public void poner(double cuanto) {

    this.validarMontoPositivo(cuanto);
    this.validarNoExcederCantidadDeDepositos();
    this.agregarMovimiento(LocalDate.now(), cuanto, true);
  }


  public void sacar(double cuanto) {
    this.validarMontoPositivo(cuanto);
    this.validarExtraccionMenorASaldo(cuanto);
    this.validarMontoExtraidoNoSuperaLimite(cuanto);
    this.agregarMovimiento(LocalDate.now(), cuanto, false);

  }
  public boolean validarNoExcederCantidadDeDepositos(){
    if(this.movimientosDe(LocalDate.now()).size() >= this.getLimiteDepositos()){
      throw new MaximaCantidadDepositosException("Ya excedio los " + this.getLimiteDepositos() + " depositos diarios");
    }else{
      return true;
    }
  }
  public boolean validarMontoExtraidoNoSuperaLimite(double monto){
    if(monto > this.limiteExtraccion()){
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000
          + " diarios, límite: " + this.limiteExtraccion());
    }else{
      return true;
    }
  }
  public boolean validarMontoPositivo(double monto){
    if(monto<0){
      throw new MontoNegativoException(monto + ": el monto a ingresar debe ser un valor positivo");
    }else{
      return true;
    }
  }
  public double limiteExtraccion(){
    return this.getLimite() - this.getMontoExtraidoA(LocalDate.now());
  }
  public boolean validarExtraccionMenorASaldo(double monto){
    if(monto > this.getSaldo()){
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }else{
      return true;
    }
  }

  public void agregarMovimiento(LocalDate fecha, double cuanto, boolean esDeposito) {
    Movimiento movimiento = new Movimiento(fecha, cuanto, esDeposito);
    movimientos.add(movimiento);
  }


  public double getMontoExtraidoA(LocalDate fecha) {
    return
        this.movimientosDe(fecha)
        .stream()
        .filter(movimiento -> !movimiento.isDeposito())
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }
 public List<Movimiento> movimientosDe(LocalDate fecha){
    return getMovimientos().stream().filter(m -> m.esDeLaFecha(fecha)).collect(Collectors.toList());
 }
  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  public double getLimite() {
    return limite;
  }

  public void setLimite(double limite) {
    this.limite = limite;
  }

  public double getLimiteDepositos() {
    return limiteDepositos;
  }

  public void setLimiteDepositos(int limite) {
    this.limiteDepositos = limite;
  }
}



