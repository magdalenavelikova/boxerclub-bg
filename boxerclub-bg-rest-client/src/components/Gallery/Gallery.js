import { useState } from "react";
import { Container } from "react-bootstrap";
import PhotoAlbum from "react-photo-album";
import Lightbox from "yet-another-react-lightbox";
import "yet-another-react-lightbox/styles.css";
import slides from "../../data/slides";

export const Gallery = () => {
  const [index, setIndex] = useState(-1);

  return (
    <Container fluid className='p-5 mt-5'>
      <PhotoAlbum
        layout='rows'
        photos={slides}
        targetRowHeight={150}
        onClick={({ index: current }) => setIndex(current)}
      />

      <Lightbox
        index={index}
        slides={slides}
        open={index >= 0}
        close={() => setIndex(-1)}
      />
    </Container>
  );
};
