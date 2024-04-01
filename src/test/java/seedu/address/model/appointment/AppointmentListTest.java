package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.OverlappingAppointmentException;

public class AppointmentListTest {

    private final AppointmentList appointmentList = new AppointmentList();
    private final Appointment fridayAppointment = new Appointment("10:00-12:00 FRI");
    private final Appointment sundayAppointment = new Appointment("10:00-12:00 SUN");
    private final Appointment sundayOverlappingAppointment = new Appointment("11:00-12:00 SUN");

    @Test
    public void contains_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> appointmentList.contains(null));
    }

    @Test
    public void contains_appointmentNotInList_returnsFalse() {
        assertFalse(appointmentList.contains(fridayAppointment));
    }

    @Test
    public void contains_appointmentInList_returnsTrue() {
        appointmentList.add(fridayAppointment);
        assertTrue(appointmentList.contains(fridayAppointment));
    }

    @Test
    public void contains_overlappingAppointment_returnsTrue() {
        appointmentList.add(sundayAppointment);
        assertTrue(appointmentList.hasOverlappingAppointment(sundayOverlappingAppointment));
    }

    @Test
    public void add_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> appointmentList.add(null));
    }

    @Test
    public void setAppointment_nullTargetAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> appointmentList.setAppointment(
                null, sundayAppointment));
    }

    @Test
    public void setAppointment_nullEditedAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> appointmentList.setAppointment(
                sundayAppointment, null));
    }

    @Test
    public void setAppointment_targetAppointmentNotInList_throwsAppointmentNotFoundException() {
        assertThrows(AppointmentNotFoundException.class, () -> appointmentList.setAppointment(
                sundayOverlappingAppointment, sundayOverlappingAppointment));
    }

    @Test
    public void setAppointment_editedAppointmentOverlapsWithList_throwsOverlappingAppointmentException() {
        appointmentList.add(sundayAppointment);
        appointmentList.add(fridayAppointment);
        assertThrows(OverlappingAppointmentException.class, () -> appointmentList.setAppointment(
                sundayAppointment, sundayOverlappingAppointment));
    }

    @Test
    public void setAppointment_editedAppointmentIsSameAppointment_success() {
        appointmentList.add(sundayAppointment);
        appointmentList.setAppointment(sundayAppointment, sundayAppointment);
        AppointmentList expectedAppointmentList = new AppointmentList();
        expectedAppointmentList.add(sundayAppointment);
        assertEquals(appointmentList, expectedAppointmentList);
    }

    @Test
    public void remove_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> appointmentList.remove(null));
    }

    @Test
    public void remove_appointmentDoesNotExist_throwsAppointmentNotFoundException() {
        assertThrows(AppointmentNotFoundException.class, () -> appointmentList.remove(sundayAppointment));
    }

    @Test
    public void remove_existingAppointment_removesAppointment() {
        appointmentList.add(sundayAppointment);
        appointmentList.remove(sundayAppointment);
        AppointmentList expectedAppointmentList = new AppointmentList();
        assertEquals(expectedAppointmentList, appointmentList);
    }

    @Test
    public void setAppointments_nullAppointmentList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> appointmentList.setAppointments((AppointmentList) null));
    }

    @Test
    public void setAppointments_appointmentList_replacesOwnListWithProvidedAppointmentList() {
        appointmentList.add(fridayAppointment);
        AppointmentList expectedAppointmentList = new AppointmentList();
        expectedAppointmentList.add(fridayAppointment);
        appointmentList.setAppointments(expectedAppointmentList);
        assertEquals(expectedAppointmentList, appointmentList);
    }

    @Test
    public void setAppointments_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> appointmentList.setAppointments((List<Appointment>) null));
    }

    @Test
    public void setAppointments_list_replacesOwnListWithProvidedList() {
        appointmentList.add(sundayAppointment);
        List<Appointment> appointments = Collections.singletonList(fridayAppointment);
        appointmentList.setAppointments(appointments);
        AppointmentList expectedAppointmentList = new AppointmentList();
        expectedAppointmentList.add(fridayAppointment);
        assertEquals(expectedAppointmentList, appointmentList);
    }

    @Test
    public void setAppointment_listWithOverlappingAppointments_throwsOverlappingAppointmentException() {
        List<Appointment> listWithOverlappingAppointments = Arrays.asList(sundayAppointment,
                sundayOverlappingAppointment);
        assertThrows(OverlappingAppointmentException.class, () -> appointmentList.setAppointments(
                listWithOverlappingAppointments));
    }

}
