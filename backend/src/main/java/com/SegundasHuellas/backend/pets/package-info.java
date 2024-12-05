/**
 * El módulo de mascotas maneja toda la funcionalidad relacionada con mascotas en el sistema de adopción.
 * <p>
 * Este módulo es responsable de:
 * - Administrar los registros de mascotas
 * - Rastrear el estado de las mascotas (disponible, pendiente, adoptado)
 * - Notificar a otros módulos sobre eventos relacionados con mascotas
 * <p>
 * La API pública se expone a través del paquete 'api'.
 * Los detalles de implementación se mantienen en el paquete 'internal'.
 */
@ApplicationModule(displayName = "Pets")
package com.SegundasHuellas.backend.pets;

import org.springframework.modulith.ApplicationModule;