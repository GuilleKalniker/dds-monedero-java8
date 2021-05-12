package dds.monedero.model;


import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class MovimientoTest {


  private Movimiento movimientoDeposito = new Movimiento(LocalDate.now(),300,true);
  private Movimiento movimientoExtraccion = new Movimiento(LocalDate.now(),300,false);
  private Cuenta cuenta = new Cuenta();

  @Test
  void ElMovimientoEsDeposito() {

    assertTrue(movimientoDeposito.isDeposito());
  }
  @Test
  void ElMovimientoEsExtraccion() {

    assertTrue(movimientoDeposito.isExtraccion());
  }
  @Test
  void ElMovimientoTieneUnaFecha() {
    assertEquals(movimientoDeposito.getFecha(), LocalDate.now());
  }
}
