import { Header, Card, Footer } from '../components/'

function CardsPage() {
  return (
    <>
      <Header />
      <div className="App">
        <Card
          image="1"
          name="Buddy"
          age="Edad: 2 años"
          location="Ubicación: Lugar donde se encuentra."
        />

        <Card
          image="2"
          name="Raza"
          age="Edad: 1 años"
          location="Ubicación: Lugar donde se encuentra."
        />

        <Card
          image="3"
          name="Raza"
          age="Edad: 5 años"
          location="Ubicación: Lugar donde se encuentra."
        />

        <Card
          image="4"
          name="Raza"
          age="Edad: 2 años"
          location="Ubicación: Lugar donde se encuentra."
        />

        <Card
          image="5"
          name="Raza"
          age="Edad: 6 años"
          location="Ubicación: Lugar donde se encuentra."
        />

        <Card
          image="6"
          name="Raza"
          age="Edad: 9 meses"
          location="Ubicación: Lugar donde se encuentra."
        />
        <Card
          image="7"
          name="Raza"
          age="Edad: 6 años"
          location="Ubicación: Lugar donde se encuentra."
        />

        <Card
          image="8"
          name="Raza"
          age="Edad: 2 años"
          location="Ubicación: Lugar donde se encuentra."
        />
      </div>
      <Footer />
    </>
  );
}

export default CardsPage;
