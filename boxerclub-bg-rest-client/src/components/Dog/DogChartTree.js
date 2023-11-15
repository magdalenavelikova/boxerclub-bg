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
  const { selectedDog } = useContext(DogContext);
  const containerStyles = {
    width: "100vw",
    height: "100vh",
  };
  useEffect(() => {
    setDogChart(selectedDog);
  }, []);
  useEffect(() => {
    setDogChart(selectedDog);
  }, [selectedDog]);

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
          PK: {nodeDatum.attributes?.sex}
        </text>
      )}
      {nodeDatum.attributes?.color && (
        <text fill='black' x='-40' dy='100' strokeWidth='0'>
          {t("color")}: {nodeDatum.attributes?.color}
        </text>
      )}
    </g>
  );
  /* const renderRectSvgNode = ({ nodeDatum, toggleNode }) => (
    <g>
      <rect width='20' height='20' x='-20' onClick={toggleNode} />
      <text fill='black' strokeWidth='1' x='20'>
        {nodeDatum.name}
      </text>
      {nodeDatum.attributes?.registrationNumber && (
        <text fill='black' x='20' dy='20' strokeWidth='1'>
          PK: {nodeDatum.attributes?.registrationNumber}
        </text>
      )}
      {nodeDatum.attributes?.color && (
        <text fill='black' x='20' dy='40' strokeWidth='1'>
          {t("color")}: {nodeDatum.attributes?.color}
        </text>
      )}
    </g>
  );*/
  const verticalPathFunc = (linkDatum, orientation) => {
    const { source, target } = linkDatum;
    const x1 = orientation === "horizontal" ? source.y : source.x;
    const y1 = orientation === "horizontal" ? source.x : source.y;
    const x2 = orientation === "horizontal" ? target.y : target.x;
    const y2 = orientation === "horizontal" ? target.x : target.y;
    return `M${x1},${y1}L${x2},${y2}`;
  };
  const straightPathFunc = (linkDatum, orientation) => {
    const { source, target } = linkDatum;
    return orientation === "horizontal"
      ? `M${source.y},${source.x}L${target.y},${target.x}`
      : `M${source.x},${source.y}L${target.x},${target.y}`;
  };

  const straightVerticalPathFunc = (linkDatum, orientation) => {
    const { source, target } = linkDatum;
    const x1 = orientation === "horizontal" ? source.y : source.x;
    const y1 = orientation === "horizontal" ? source.x : source.y;
    const x2 = orientation === "horizontal" ? target.y : target.x;
    const y2 = orientation === "horizontal" ? target.x : target.y;
    const cx = (x1 + x2) / 2; // Calculate the midpoint for a straight line
    return `M${x1},${y1}L${cx},${y1}L${cx},${y2}L${x2},${y2}`;
  };
  const [dimensions, translate, containerRef] = useCenteredTree();
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
