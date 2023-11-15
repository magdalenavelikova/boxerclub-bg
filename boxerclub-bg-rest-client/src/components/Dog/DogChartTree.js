import React, { useContext, useEffect, useState } from "react";
import { Container, Modal } from "react-bootstrap";
import Tree from "react-d3-tree";
import { useCenteredTree } from "../../helpers";
import { DogContext } from "../../contexts/DogContext";
import { useTranslation } from "react-i18next";
// This is a simplified example of an org chart with a depth of 2.
// Note how deeper levels are defined recursively via the `children` property.
import * as formatString from "../../utils/StringUtils";

// Here we're using `renderCustomNodeElement` to represent each node
// as an SVG `rect` instead of the default `circle`.

export default function DogChartTree() {
  const { t } = useTranslation();
  const [show, setShow] = useState(true);
  const [dogChart, setDogChart] = useState({});
  const { chartDog } = useContext(DogContext);
  const [dimensions, translate, containerRef] = useCenteredTree();
  const containerStyles = {
    width: "100vw",
    height: "100vh",
  };
  useEffect(() => {
    setDogChart(chartDog);
  }, []);
  useEffect(() => {
    setDogChart(chartDog);
  }, [chartDog]);

  const handleClose = () => {
    setShow(false);
    window.history.back();
  };

  const renderRectSvgNode = ({ nodeDatum, toggleNode }) => (
    <g>
      <circle r='15' onClick={toggleNode} />
      <text fill='red' strokeWidth='1' x='-20' dy='40'>
        {formatString.formatStringToWrap(`${nodeDatum.name}`)}
      </text>
      {nodeDatum.attributes?.sex && (
        <text fill='black' x='-40' dy='80' strokeWidth='0'>
          {t("sex")}: {t(`${nodeDatum.attributes?.sex}`)}
        </text>
      )}
      {nodeDatum.attributes?.color && (
        <text fill='black' x='-40' dy='100' strokeWidth='0'>
          {t("color")}: {t(`${nodeDatum.attributes?.color}`)}
        </text>
      )}
    </g>
  );

  return (
    <Modal show={show} fullscreen onHide={() => handleClose()}>
      <Modal.Header closeButton>
        <Modal.Title className='text-secondary'>
          {t("nav.Pedigree")}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <div id='treeWrapper' style={containerStyles} ref={containerRef}>
          <Tree
            dimensions={dimensions}
            translate={translate}
            renderCustomNodeElement={renderRectSvgNode}
            orientation='vertical'
            data={dogChart}
          />
        </div>
      </Modal.Body>
    </Modal>
  );
}
