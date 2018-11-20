package manage.route.sercice.impl;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.CommonUtil;
import java.util.ArrayList;
import java.util.List;
import manage.domain.pojo.DomainBean;
import manage.equt.pojo.EqutInfoBean;
import manage.generator.pojo.GeneratorInfoBean;
import manage.pipe.pojo.FaceInfoBean;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.pipe.pojo.TubeInfoBean;
import manage.pipe.pojo.WellInfoBean;
import manage.poleline.pojo.PoleInfoBean;
import manage.poleline.pojo.PolelineSegmentInfoBean;
import manage.route.pojo.CableInfoBean;
import manage.route.pojo.CableRouteInfoBean;
import manage.route.pojo.FiberBoxInfoBean;
import manage.route.pojo.OppoFiberInfoBean;
import manage.route.pojo.RouteInfoBean;
import manage.route.sercice.RouteService;

public class RouteServiceImpl extends DataBase
  implements RouteService
{
  private static String GET_ROUTE_BY_PAGE = "route.getRouteByPage";

  private static String GET_ROUTE_COUNT = "route.getRoutCount";

  private static String GET_CABLE_BY_PAGE = "route.getCableByPage";

  private static String GET_CABLE_COUNT = "route.getCableCount";

  private static String INSERT_ROUTE = "route.insertRoute";
  private static final String UPDATE_POINT_BY_CABLE = "route.updatePointByCable";
  private static final String UPDATE_CABLE_BY_ROUTE = "route.updateCableByRoute";
  private static final String UPDATE_CABLE = "route.updateCable";
  private static final String DELETE_ROUTE = "route.deleteRoute";
  private static final String GET_VERIFY_ROUTE = "route.getVerifyRoute";
  private static final String UPDATE_ROUTE = "route.updateRoute";
  private static final String INSERT_CABLE = "route.insertCable";
  private static final String GET_VERIFY_CABLE = "route.getVerifyCable";
  private static final String DELETE_CABLE = "route.deleteCable";
  private static final String UPDATE_POINT_BY_ROUTE = "route.updatePointByRoute";
  private static final String GET_OPPO_FIBER = "route.getOppoFiber";
  private static final String GET_EQUT = "route.getEqutBean";
  private static final String GetCableStatement = "route.getCable";
  private static final String UpdateCableRouteId = "route.updateCableRouteId";
  private static final String GetTubeToCableStatement = "route.getTubeToCable";
  private static final String InsertRouteStatement = "route.insertRoutes";
  private static final String UpdateJbCableStatment = "route.updateJbCable";
  private static final String JbCableListStatment = "route.getJbCable";
  private static final String CableRouteListStatment = "route.getCableRoute";
  private static final String TubeSegment = "route.tubeSegment";
  private static final String GetPipeSegmentStatment = "route.getPipeSeg";
  private static final String GET_WELL = "pdaresourcecheck.getWell";
  private static final String GET_FACE = "pdaresourcecheck.getFace";
  private static final String GET_POLE = "pdaresourcecheck.getPole";
  private static final String GET_FACE_RELATION = "pdaresourcecheck.getFaceRelation";
  private static final String PolelineSegment = "route.polelineSegment";
  private static final String PoleStatment = "route.getPole";
  private static final String InsertCableRoute = "route.insertCableRoute";
  private static final String UpdateTubeStatment = "route.updateTube";
  private static final String CableRouteByIdStatment = "route.cableRouteByIdStatment";
  private static final String UpdateCableRouteStatment = "route.updateCableRouteStatment";
  private static final String TubeListStatment = "route.getTubeList";
  private static final String DeleteCableRouteStatement = "route.deleteCableRoute";
  private static final String TubeCountStatment = "route.getTubeCount";
  private static final String PolelineSegmentStatment = "route.getPolelineSegmentList";
  private static final String PolelineSegmentCountStatment = "route.getPolelineSegmentCount";
  private static final String GetRouteStatement = "route.getRoute";
  private static final String CableRouteCountStatment = "route.getCableRouteCount";
  private static final String TubeRepeatListStatment = "route.getTubeRepeatList";
  private static final String TubeRepeatCountStatment = "route.getTubeRepeatCount";
  private static final String UpdateCableStatment1 = "route.updateCable1";
  private static final String DeleteRouteStatement1 = "route.deleteroute1";
  private static final String CableListStatment = "route.getCableList";
  private static final String DeleteCableStatment = "route.deleteCableStatment";
  private static final String InsertCableStatement = "route.insertCable1";
  private static final String CRstatement = "route.getCR";
  private static final String UpdateCableRouteStatment1 = "route.updateCableRouteStatment1";
  private static final String FaceRelationStatment = "route.getFaceRelation";
  private static final String FiberboxListStatment = "route.getFiberlist";
  private static final String FiberboxCountStatment = "route.getFibertotal";
  private static String GET_DOMAIN = "route.getDomain";
  private static String GET_GENERATOR = "route.getGenerator";

  public RouteInfoBean getRouteGridrByPage(RouteInfoBean searchRoute)
    throws DataAccessException
  {
    List rlist = getObjects(GET_ROUTE_BY_PAGE, searchRoute);

    int total = getCount(GET_ROUTE_COUNT, searchRoute);
    RouteInfoBean routeGrid = new RouteInfoBean();
    routeGrid.setRoutes(rlist);
    routeGrid.setTotal(Integer.valueOf(total));
    return routeGrid;
  }

  public CableInfoBean getCableGridrByPage(CableInfoBean searchCable)
    throws DataAccessException
  {
    List Clist = getObjects(GET_CABLE_BY_PAGE, searchCable);

    int total = getCount(GET_CABLE_COUNT, searchCable);
    CableInfoBean cableGrid = new CableInfoBean();
    cableGrid.setCables(Clist);
    cableGrid.setTotal(Integer.valueOf(total));
    return cableGrid;
  }

  public int getVerifyRoute(RouteInfoBean newRoute)
    throws DataAccessException
  {
    List rlist = getObjects("route.getVerifyRoute", newRoute);
    if ((newRoute.getRouteid() == null) || ("".equals(newRoute.getRouteid())))
    {
      return ((rlist == null) ? 0 : rlist.size());
    }
    if ((rlist == null) || (rlist.isEmpty())) {
      return 0;
    }
    RouteInfoBean route = (RouteInfoBean)rlist.get(0);
    return ((route.getRouteid().intValue() == newRoute.getRouteid().intValue()) ? 0 : 1);
  }

  public int insertNewRoute(RouteInfoBean newRoute)
    throws DataAccessException
  {
    insert(INSERT_ROUTE, newRoute);
    return 1;
  }

  public int stopMergeRoute(String ids, RouteInfoBean mergeRoute)
    throws DataAccessException
  {
    String[] id = ids.split(",");
    for (int i = 0; i < id.length; ++i) {
      RouteInfoBean route = new RouteInfoBean();
      route.setRouteid(Integer.valueOf(Integer.parseInt(id[i].trim())));
      route = (RouteInfoBean)getObject(GET_ROUTE_BY_PAGE, route);
      route.setNewRoutename(mergeRoute.getRoutename());
      update("route.updateCableByRoute", route);
      update("route.updatePointByRoute", route);

      delete("route.deleteRoute", route);
    }
    insert(INSERT_ROUTE, mergeRoute);
    return 1;
  }

  public int updateRouteBean(RouteInfoBean updateRoute)
    throws DataAccessException
  {
    int n = update("route.updateRoute", updateRoute);
    update("route.updateCableByRoute", updateRoute);
    update("route.updatePointByRoute", updateRoute);
    return n;
  }

  public int insertNewCable(CableInfoBean newCable)
    throws DataAccessException
  {
    insert("route.insertCable", newCable);
    return 1;
  }

  public int getVerifyCable(CableInfoBean newCable)
    throws DataAccessException
  {
    List clist = getObjects("route.getVerifyCable", newCable);
    if (newCable.getCableid() == null) {
      return ((clist == null) ? 0 : clist.size());
    }
    if ((clist == null) || (clist.isEmpty())) {
      return 0;
    }
    CableInfoBean cable = (CableInfoBean)clist.get(0);
    return ((cable.getCableid().intValue() == newCable.getCableid().intValue()) ? 0 : 1);
  }

  public int stopMergeCable(String ids, CableInfoBean mergeCable)
    throws DataAccessException
  {
    String[] cableIds = ids.split(",");
    for (String cableid : cableIds) {
      CableInfoBean cable = new CableInfoBean();
      cable.setCableid(Integer.valueOf(Integer.parseInt(cableid.trim())));
      cable = (CableInfoBean)getObject(GET_CABLE_BY_PAGE, cable);
      cable.setNewCablename(mergeCable.getNewCablename());
      update("route.updatePointByCable", cable);
      delete("route.deleteCable", cable);
    }
    String cablename = mergeCable.getNewCablename();
    String routename = mergeCable.getNewRoutename();
    mergeCable.setCablename(cablename);
    mergeCable.setRoutename(routename);
    insert("route.insertCable", mergeCable);
    return 1;
  }

  public int updateCableBean(CableInfoBean updateCable)
    throws DataAccessException
  {
    int n = update("route.updateCable", updateCable);
    update("route.updatePointByCable", updateCable);
    return n;
  }

  public OppoFiberInfoBean searchOppoFiber(OppoFiberInfoBean searchOppoFiber)
    throws DataAccessException
  {
    OppoFiberInfoBean oppoFiberGrid = new OppoFiberInfoBean();
    List list = getObjects("route.getOppoFiber", searchOppoFiber);
    oppoFiberGrid.setOppoFibers(list);
    return oppoFiberGrid;
  }

  public EqutInfoBean getEqutBean(EqutInfoBean equtInfoBean) throws DataAccessException {
    EqutInfoBean e = (EqutInfoBean)getObject("route.getEqutBean", equtInfoBean);
    return e; }

  public CableInfoBean getCable(CableInfoBean updateCable) throws DataAccessException {
    CableInfoBean c = new CableInfoBean();
    c = (CableInfoBean)getObject("route.getCable", updateCable);
    return c; }

  public CableInfoBean getTubeToCable(CableInfoBean cableInfoBean) throws DataAccessException {
    return ((CableInfoBean)getObject("route.getTubeToCable", cableInfoBean)); }

  public Integer updateCableRouteId(CableInfoBean cableInfoBean) throws DataAccessException {
    return Integer.valueOf(update("route.updateCableRouteId", cableInfoBean)); }

  public Integer updateJbCable(CableInfoBean cableInfoBean) throws DataAccessException {
    int rows = update("route.updateJbCable", cableInfoBean);
    return Integer.valueOf(rows); }

  public Integer insertRoute(RouteInfoBean routeInfoBean) throws DataAccessException {
    return ((Integer)insert("route.insertRoutes", routeInfoBean)); }

  public CableInfoBean getJbCableList(CableInfoBean cableInfoBean) throws DataAccessException {
    List cables = getObjects("route.getJbCable", cableInfoBean);
    CableInfoBean c = new CableInfoBean();
    c.setCables(cables);
    return c;
  }

  public List<CableRouteInfoBean> getCableRouteList(CableRouteInfoBean cableRouteInfo) throws DataAccessException {
    List<CableRouteInfoBean> resultList = getObjects("route.getCableRoute", cableRouteInfo);
    for (CableRouteInfoBean c : resultList) {
      if ((c.getTubeId() != null) && (c.getTubeId().intValue() != 0)) {
        TubeInfoBean t = new TubeInfoBean();
        t.setTubeId(c.getTubeId());
        t = (TubeInfoBean)getObject("route.tubeSegment", t);
        if ((t != null) && 
          (t.getPipeSegmentId() != null) && (!(t.getPipeSegmentId().equals("")))) {
          PipeSegmentInfoBean pSeg = new PipeSegmentInfoBean();
          pSeg.setPipeSegmentId(Integer.valueOf(Integer.parseInt(t.getPipeSegmentId())));
          pSeg = (PipeSegmentInfoBean)getObject("route.getPipeSeg", pSeg);
          if (pSeg != null) {
            String type = pSeg.getPipeSegmentType();
            Integer wId;
            WellInfoBean w;
            FaceInfoBean face;
            if (type.equals("2")) {
              wId = pSeg.getStartDeviceId();
              if ((wId != null) && (wId.intValue() != 0)) {
                w = new WellInfoBean();
                w.setWellId(wId);
                w = (WellInfoBean)getObject("pdaresourcecheck.getWell", w);
                if (w != null) {
                  c.setStartFacilityName(w.getWellNameSub());
                }
                face = new FaceInfoBean();
                face.setWellId(wId);
                face.setPipeSegmentId(t.getPipeSegmentId());
                face = (FaceInfoBean)getObject("pdaresourcecheck.getFace", face);
                if (face != null) {
                  c.setStartFaceNo(face.getLocationNo());
                }
                c.setStartTubeName(t.getTubeNumber());
              }

              Integer pId = pSeg.getEndDeviceId();
              if ((pId != null) && (pId.intValue() != 0)) {
                PoleInfoBean po = new PoleInfoBean();
                po.setPoleId(pId);
                po = (PoleInfoBean)getObject("pdaresourcecheck.getPole", po);
                if (po != null)
                  c.setEndFacilityName(po.getPoleNameSub());
              }
            }
            else
            {
              if (type.equals("3")) {
                Integer pId = pSeg.getStartDeviceId();
                if ((pId != null) && (pId.intValue() != 0)) {
                  PoleInfoBean po = new PoleInfoBean();
                  po.setPoleId(pId);
                  po = (PoleInfoBean)getObject("pdaresourcecheck.getPole", po);
                  if (po != null) {
                    c.setStartFacilityName(po.getPoleNameSub());
                  }
                }

                wId = pSeg.getEndDeviceId();
                if ((wId != null) && (wId.intValue() != 0)) {
                  w = new WellInfoBean();
                  w.setWellId(wId);
                  w = (WellInfoBean)getObject("pdaresourcecheck.getWell", w);
                  if (w != null) {
                    c.setEndFacilityName(w.getWellNameSub());
                  }
                  face = new FaceInfoBean();
                  face.setWellId(wId);
                  face.setPipeSegmentId(t.getPipeSegmentId());
                  face = (FaceInfoBean)getObject("pdaresourcecheck.getFace", face);
                  if (face != null) {
                    c.setEndFaceNo(face.getLocationNo());
                  }
                  c.setEndTubeName(t.getTubeNumber());
                }
              } else {
                wId = pSeg.getStartDeviceId();
                if ((wId != null) && (wId.intValue() != 0)) {
                  w = new WellInfoBean();
                  w.setWellId(wId);
                  w = (WellInfoBean)getObject("pdaresourcecheck.getWell", w);
                  if (w != null) {
                    c.setStartFacilityName(w.getWellNameSub());
                  }
                  if ((t.getWellId() != null) && 
                    (wId.intValue() == Integer.parseInt(t.getWellId()))) {
                	  FaceInfoBean fw = new FaceInfoBean();
                	  fw.setWellId(wId);
                	  fw.setPipeSegmentId(t.getPipeSegmentId());
                	  fw = (FaceInfoBean)getObject("pdaresourcecheck.getFace", fw);
                    if (fw != null) {
                      c.setStartFaceNo(fw.getLocationNo());
                      c.setStartTubeName(t.getTubeNumber());

                      FaceInfoBean fR = new FaceInfoBean();
                      fR = (FaceInfoBean)getObject("pdaresourcecheck.getFaceRelation", fw);
                      FaceInfoBean face2 = new FaceInfoBean();
                      if (fR != null) {
                        if (fR.getFaceId1().intValue() == fw.getFaceId().intValue())
                          face2.setFaceId(fR.getFaceId2());
                        else {
                          face2.setFaceId(fR.getFaceId1());
                        }
                        face2 = (FaceInfoBean)getObject("pdaresourcecheck.getFace", face2);
                        if (face2 != null) {
                          c.setEndFaceNo(face2.getLocationNo());
                        }
                        if (("1".equals(t.getIsFather())) || 
                          ("3".equals(t.getIsFather()))) {
                          c.setEndTubeName(CommonUtil.getOppoTubeNumber(t, fw.getCols().intValue()));
                        }
                        if ("2".equals(t.getIsFather())) {
                          TubeInfoBean fatherTube = new TubeInfoBean();
                          fatherTube.setTubeId(t.getFatherPoreId());
                          fatherTube = (TubeInfoBean)getObject("route.tubeSegment", fatherTube);
                          c.setEndTubeName(CommonUtil.getOppoTubeNumber(t, fw.getCols().intValue(), fatherTube.getSubTubeAmount().intValue()));
                        }
                      }
                    }
                  }

                }

                Integer wId2 = pSeg.getEndDeviceId();
                if ((wId2 != null) && (wId2.intValue() != 0)) {
                  w = new WellInfoBean();
                  w.setWellId(wId2);
                  w = (WellInfoBean)getObject("pdaresourcecheck.getWell", w);
                  if (w != null) {
                    c.setEndFacilityName(w.getWellNameSub());
                  }
                  if ((t.getWellId() != null) && 
                    (wId2.intValue() == Integer.parseInt(t.getWellId()))) {
                    face = new FaceInfoBean();
                    face.setWellId(wId2);
                    face.setPipeSegmentId(t.getPipeSegmentId());
                    face = (FaceInfoBean)getObject("pdaresourcecheck.getFace", face);
                    if (face != null) {
                      c.setEndFaceNo(face.getLocationNo());
                      c.setEndTubeName(t.getTubeNumber());

                      FaceInfoBean fR = new FaceInfoBean();
                      fR = (FaceInfoBean)getObject("pdaresourcecheck.getFaceRelation", face);
                      FaceInfoBean face2 = new FaceInfoBean();
                      if (fR != null) {
                        if (fR.getFaceId1().intValue() == face.getFaceId().intValue())
                          face2.setFaceId(fR.getFaceId2());
                        else {
                          face2.setFaceId(fR.getFaceId1());
                        }
                        face2 = (FaceInfoBean)getObject("pdaresourcecheck.getFace", face2);
                        if (face2 != null) {
                          c.setStartFaceNo(face2.getLocationNo());
                        }
                        if (("1".equals(t.getIsFather())) || 
                          ("3".equals(t.getIsFather()))) {
                          c.setStartTubeName(CommonUtil.getOppoTubeNumber(t, face.getCols().intValue()));
                        }
                        if ("2".equals(t.getIsFather())) {
                          TubeInfoBean fatherTube = new TubeInfoBean();
                          fatherTube.setTubeId(t.getFatherPoreId());
                          fatherTube = (TubeInfoBean)getObject("route.tubeSegment", fatherTube);
                          c.setStartTubeName(CommonUtil.getOppoTubeNumber(t, face.getCols().intValue(), fatherTube.getSubTubeAmount().intValue()));
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }

      if ((c.getIdE() != null) && (c.getIdE().intValue() != 0)) {
        PolelineSegmentInfoBean polelineSegmentInfoBean = new PolelineSegmentInfoBean();
        polelineSegmentInfoBean.setPoleLineSegmentId(c.getIdE());
        polelineSegmentInfoBean = (PolelineSegmentInfoBean)getObject("route.polelineSegment", polelineSegmentInfoBean);
        if (polelineSegmentInfoBean != null)
        {
          PoleInfoBean poleInfoBean;
          if ((polelineSegmentInfoBean.getStartDeviceId() != null) && (polelineSegmentInfoBean.getStartDeviceId().intValue() != 0)) {
            poleInfoBean = new PoleInfoBean();
            poleInfoBean.setPoleId(polelineSegmentInfoBean.getStartDeviceId());
            poleInfoBean = (PoleInfoBean)getObject("route.getPole", poleInfoBean);
            if (poleInfoBean != null) {
              c.setStartFacilityName(poleInfoBean.getPoleNameSub());
            }
          }
          if ((polelineSegmentInfoBean.getEndDeviceId() != null) && (polelineSegmentInfoBean.getEndDeviceId().intValue() != 0)) {
            poleInfoBean = new PoleInfoBean();
            poleInfoBean.setPoleId(polelineSegmentInfoBean.getEndDeviceId());
            poleInfoBean = (PoleInfoBean)getObject("route.getPole", poleInfoBean);
            if (poleInfoBean != null) {
              c.setEndFacilityName(poleInfoBean.getPoleNameSub());
            }
          }
        }
      }
    }
    if ((resultList != null) && (resultList.size() != 0)) {
      resultList = distinct(resultList);
      return resultList;
    }
    return new ArrayList();
  }

  public int getCableRouteCount(CableRouteInfoBean cableRouteInfoBean)
    throws DataAccessException
  {
    return getCount("route.getCableRouteCount", cableRouteInfoBean);
  }

  private List<CableRouteInfoBean> distinct(List<CableRouteInfoBean> resultList) {
    List newList = new ArrayList();
    for (CableRouteInfoBean cr : resultList) {
      if (!(newList.contains(cr))) {
        newList.add(cr);
      }
    }
    return newList;
  }

  public PolelineSegmentInfoBean getPolelineSegment(PolelineSegmentInfoBean polelineSegmentInfo) throws DataAccessException
  {
    PolelineSegmentInfoBean polelineSegmentInfoBean = (PolelineSegmentInfoBean)getObject("route.polelineSegment", polelineSegmentInfo);
    return polelineSegmentInfoBean;
  }

  public TubeInfoBean getTubeById(TubeInfoBean tubeInfo) throws DataAccessException {
    TubeInfoBean tubeInfoBean = (TubeInfoBean)getObject("route.tubeSegment", tubeInfo);
    return tubeInfoBean;
  }

  public PipeSegmentInfoBean getPipeSegmentInfoBean(PipeSegmentInfoBean pipeSegmentInfoBean) throws DataAccessException
  {
    PipeSegmentInfoBean pipeSegmentInfo = (PipeSegmentInfoBean)getObject("route.getPipeSeg", pipeSegmentInfoBean);
    return pipeSegmentInfo;
  }

  public void insertCableRoute(CableRouteInfoBean cableRouteInfoBean) throws DataAccessException {
    insert("route.insertCableRoute", cableRouteInfoBean);
  }

  public Integer updateTube(TubeInfoBean tubeInfoBean) throws DataAccessException {
    int rows = update("route.updateTube", tubeInfoBean);
    return Integer.valueOf(rows); }

  public CableRouteInfoBean getCableRoute(CableRouteInfoBean cableRouteInfo) throws DataAccessException {
    CableRouteInfoBean cableRouteInfoBean = (CableRouteInfoBean)getObject("route.cableRouteByIdStatment", cableRouteInfo);
    return cableRouteInfoBean;
  }

  public Integer updatecableRoute(CableRouteInfoBean cableRouteInfoBean) throws DataAccessException {
    int rows = update("route.updateCableRouteStatment", cableRouteInfoBean);
    return Integer.valueOf(rows);
  }

  public int deleteCableRoute(CableRouteInfoBean cableRouteInfoBean) throws DataAccessException {
    CableRouteInfoBean cableRouteInfoBean2 = new CableRouteInfoBean();
    cableRouteInfoBean2.setOptCableId(cableRouteInfoBean.getOptCableId());
    List<CableRouteInfoBean> resultList = getObjects("route.getCableRoute", cableRouteInfoBean2);
    for (CableRouteInfoBean c : resultList) {
      if ((c.getTubeId() != null) && (c.getTubeId().intValue() != 0)) {
        TubeInfoBean t = new TubeInfoBean();
        t.setTubeId(c.getTubeId());
        t = (TubeInfoBean)getObject("route.tubeSegment", t);
        if ((t != null) && 
          (t.getPipeSegmentId() != null) && (!(t.getPipeSegmentId().equals("")))) {
          PipeSegmentInfoBean pSeg = new PipeSegmentInfoBean();
          pSeg.setPipeSegmentId(Integer.valueOf(Integer.parseInt(t.getPipeSegmentId())));
          pSeg = (PipeSegmentInfoBean)getObject("route.getPipeSeg", pSeg);
          if (pSeg != null) {
            String type = pSeg.getPipeSegmentType();
            Integer wId;
            WellInfoBean w;
            FaceInfoBean face;
            if (type.equals("2")) {
              wId = pSeg.getStartDeviceId();
              if ((wId != null) && (wId.intValue() != 0)) {
                w = new WellInfoBean();
                w.setWellId(wId);
                w = (WellInfoBean)getObject("pdaresourcecheck.getWell", w);
                if (w != null) {
                  c.setStartFacilityName(w.getWellName());
                }
                face = new FaceInfoBean();
                face.setWellId(wId);
                face.setPipeSegmentId(t.getPipeSegmentId());
                face = (FaceInfoBean)getObject("pdaresourcecheck.getFace", face);
                if (face != null) {
                  c.setStartFaceNo(face.getLocationNo());
                }
                c.setStartTubeName(t.getTubeNumber());
              }

              Integer pId = pSeg.getEndDeviceId();
              if ((pId != null) && (pId.intValue() != 0)) {
                PoleInfoBean po = new PoleInfoBean();
                po.setPoleId(pId);
                po = (PoleInfoBean)getObject("pdaresourcecheck.getPole", po);
                if (po != null)
                  c.setEndFacilityName(po.getPoleName());
              }
            }
            else
            {
              if (type.equals("3")) {
                Integer pId = pSeg.getStartDeviceId();
                if ((pId != null) && (pId.intValue() != 0)) {
                  PoleInfoBean po = new PoleInfoBean();
                  po.setPoleId(pId);
                  po = (PoleInfoBean)getObject("pdaresourcecheck.getPole", po);
                  if (po != null) {
                    c.setStartFacilityName(po.getPoleName());
                  }
                }
                wId = pSeg.getEndDeviceId();
                if ((wId != null) && (wId.intValue() != 0)) {
                  w = new WellInfoBean();
                  w.setWellId(wId);
                  w = (WellInfoBean)getObject("pdaresourcecheck.getWell", w);
                  if (w != null) {
                    c.setEndFacilityName(w.getWellName());
                  }
                  face = new FaceInfoBean();
                  face.setWellId(wId);
                  face.setPipeSegmentId(t.getPipeSegmentId());
                  face = (FaceInfoBean)getObject("pdaresourcecheck.getFace", face);
                  if (face != null) {
                    c.setEndFaceNo(face.getLocationNo());
                  }
                  c.setEndTubeName(t.getTubeNumber());
                }
              } else {
                wId = pSeg.getStartDeviceId();
                if ((wId != null) && (wId.intValue() != 0)) {
                  w = new WellInfoBean();
                  w.setWellId(wId);
                  w = (WellInfoBean)getObject("pdaresourcecheck.getWell", w);
                  if (w != null) {
                    c.setStartFacilityName(w.getWellName());
                  }
                  if ((t.getWellId() != null) && 
                    (wId.intValue() == Integer.parseInt(t.getWellId()))) {
                	  FaceInfoBean fw = new FaceInfoBean();
                	  fw.setWellId(wId);
                	  fw.setPipeSegmentId(t.getPipeSegmentId());
                	  fw = (FaceInfoBean)getObject("pdaresourcecheck.getFace", fw);
                    if (w != null) {
                      c.setStartFaceNo(fw.getLocationNo());
                      c.setStartTubeName(t.getTubeNumber());

                      FaceInfoBean fR = new FaceInfoBean();
                      fR = (FaceInfoBean)getObject("pdaresourcecheck.getFaceRelation", fw);
                      FaceInfoBean face2 = new FaceInfoBean();
                      if (fR != null) {
                        if (fR.getFaceId1().intValue() == fw.getFaceId().intValue())
                          face2.setFaceId(fR.getFaceId2());
                        else {
                          face2.setFaceId(fR.getFaceId1());
                        }
                        face2 = (FaceInfoBean)getObject("pdaresourcecheck.getFace", face2);
                        if (face2 != null) {
                          c.setEndFaceNo(face2.getLocationNo());
                        }
                        if (("1".equals(t.getIsFather())) || 
                          ("3".equals(t.getIsFather()))) {
                          c.setEndTubeName(CommonUtil.getOppoTubeNumber(t, fw.getCols().intValue()));
                        }
                        if ("2".equals(t.getIsFather())) {
                          TubeInfoBean fatherTube = new TubeInfoBean();
                          fatherTube.setTubeId(t.getFatherPoreId());
                          fatherTube = (TubeInfoBean)getObject("route.tubeSegment", fatherTube);
                          c.setEndTubeName(CommonUtil.getOppoTubeNumber(t, fw.getCols().intValue(), fatherTube.getSubTubeAmount().intValue()));
                        }
                      }
                    }
                  }

                }

                Integer wId2 = pSeg.getEndDeviceId();
                if ((wId2 != null) && (wId2.intValue() != 0)) {
                  w = new WellInfoBean();
                  w.setWellId(wId2);
                  w = (WellInfoBean)getObject("pdaresourcecheck.getWell", w);
                  if (w != null) {
                    c.setEndFacilityName(w.getWellName());
                  }
                  if ((t.getWellId() != null) && 
                    (wId2.intValue() == Integer.parseInt(t.getWellId()))) {
                    face = new FaceInfoBean();
                    face.setWellId(wId2);
                    face.setPipeSegmentId(t.getPipeSegmentId());
                    face = (FaceInfoBean)getObject("pdaresourcecheck.getFace", face);
                    if (face != null) {
                      c.setEndFaceNo(face.getLocationNo());
                      c.setEndTubeName(t.getTubeNumber());

                      FaceInfoBean fR = new FaceInfoBean();
                      fR = (FaceInfoBean)getObject("pdaresourcecheck.getFaceRelation", face);
                      FaceInfoBean face2 = new FaceInfoBean();
                      if (fR != null) {
                        if (fR.getFaceId1().intValue() == face.getFaceId().intValue())
                          face2.setFaceId(fR.getFaceId2());
                        else {
                          face2.setFaceId(fR.getFaceId1());
                        }
                        face2 = (FaceInfoBean)getObject("pdaresourcecheck.getFace", face2);
                        if (face2 != null) {
                          c.setStartFaceNo(face2.getLocationNo());
                        }
                        if (("1".equals(t.getIsFather())) || 
                          ("3".equals(t.getIsFather()))) {
                          c.setStartTubeName(CommonUtil.getOppoTubeNumber(t, face.getCols().intValue()));
                        }
                        if ("2".equals(t.getIsFather())) {
                          TubeInfoBean fatherTube = new TubeInfoBean();
                          fatherTube.setTubeId(t.getFatherPoreId());
                          fatherTube = (TubeInfoBean)getObject("route.tubeSegment", fatherTube);
                          c.setStartTubeName(CommonUtil.getOppoTubeNumber(t, face.getCols().intValue(), fatherTube.getSubTubeAmount().intValue()));
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }

      if ((c.getIdE() != null) && (c.getIdE().intValue() != 0)) {
        PolelineSegmentInfoBean polelineSegmentInfoBean = new PolelineSegmentInfoBean();
        polelineSegmentInfoBean.setPoleLineSegmentId(c.getIdE());
        polelineSegmentInfoBean = (PolelineSegmentInfoBean)getObject("route.polelineSegment", polelineSegmentInfoBean);
        if (polelineSegmentInfoBean != null)
        {
          PoleInfoBean poleInfoBean;
          if ((polelineSegmentInfoBean.getStartDeviceId() != null) && (polelineSegmentInfoBean.getStartDeviceId().intValue() != 0)) {
            poleInfoBean = new PoleInfoBean();
            poleInfoBean.setPoleId(polelineSegmentInfoBean.getStartDeviceId());
            poleInfoBean = (PoleInfoBean)getObject("route.getPole", poleInfoBean);
            if (poleInfoBean != null) {
              c.setStartFacilityName(poleInfoBean.getPoleName());
            }
          }
          if ((polelineSegmentInfoBean.getEndDeviceId() != null) && (polelineSegmentInfoBean.getEndDeviceId().intValue() != 0)) {
            poleInfoBean = new PoleInfoBean();
            poleInfoBean.setPoleId(polelineSegmentInfoBean.getEndDeviceId());
            poleInfoBean = (PoleInfoBean)getObject("route.getPole", poleInfoBean);
            if (poleInfoBean != null) {
              c.setEndFacilityName(poleInfoBean.getPoleName());
            }
          }
        }
      }
    }

    if ((resultList != null) && (resultList.size() != 0))
    {
      CableRouteInfoBean oppocr;
      for (int j = 0; j < resultList.size(); ++j) {
        oppocr = (CableRouteInfoBean)resultList.get(j);
        if (cableRouteInfoBean.getId().intValue() == oppocr.getId().intValue()) {
          cableRouteInfoBean = oppocr;
        }
      }
      for (int j = 0; j < resultList.size(); ++j) {
        oppocr = (CableRouteInfoBean)resultList.get(j);
        if (cableRouteInfoBean.equals(oppocr)) {
          if ((oppocr.getTubeId() != null) && (oppocr.getTubeId().intValue() != 0) && (!(oppocr.getTubeId().equals("")))) {
            TubeInfoBean tubeOppo = new TubeInfoBean();
            tubeOppo.setTubeId(oppocr.getTubeId());
            tubeOppo = (TubeInfoBean)getObject("route.getTubeList", tubeOppo);
            String bidstr = tubeOppo.getBearCableSegmentId();
            String bnamestr = tubeOppo.getBearCableSegment();
            String copt = cableRouteInfoBean.getOptCableId()+"";
            if ((bidstr != null) && (!("".equals(bidstr)))) {
              String[] bidArr = bidstr.split(",");
              String[] bidArr2 = bnamestr.split(",");
              StringBuffer sb = new StringBuffer();
              StringBuffer sb2 = new StringBuffer();
              for (int i = 0; i < bidArr.length; ++i) {
                if (!(copt.equals(bidArr[i]))) {
                  sb.append(bidArr[i] + ",");
                  sb2.append(bidArr2[i] + ",");
                }
              }
              tubeOppo.setBearCableSegmentId(sb.toString());
              tubeOppo.setBearCableSegment(sb2.toString());
              update("route.updateTube", tubeOppo);
            }
          }
          delete("route.deleteCableRoute", oppocr);
        }
      }
    }
    return 0;
  }

  public TubeInfoBean getTubeList(TubeInfoBean tubeInfo) throws DataAccessException {
    List tubes = getObjects("route.getTubeList", tubeInfo);
    int total = getCount("route.getTubeCount", tubeInfo);
    TubeInfoBean t = new TubeInfoBean();
    t.setTubes(tubes);
    t.setTotal(Integer.valueOf(total));
    return t;
  }

  public PolelineSegmentInfoBean getPolelineSegmentList(PolelineSegmentInfoBean polelineSegmentInfo) throws DataAccessException
  {
    List polelinesegments = getObjects("route.getPolelineSegmentList", polelineSegmentInfo);
    int total = getCount("route.getPolelineSegmentCount", polelineSegmentInfo);
    PolelineSegmentInfoBean pls = new PolelineSegmentInfoBean();
    pls.setPolelinesegments(polelinesegments);
    pls.setTotal(Integer.valueOf(total));
    return pls;
  }

  public List<CableInfoBean> getCableList(RouteInfoBean routeInfoBean) throws DataAccessException {
    CableInfoBean cable = new CableInfoBean();
    cable.setRouteid(routeInfoBean.getRouteid().toString());
    return getObjects("route.getCable", cable);
  }

  public RouteInfoBean getRoute(RouteInfoBean routeInfoBean) throws DataAccessException
  {
    return ((RouteInfoBean)getObject("route.getRoute", routeInfoBean));
  }

  public void saveRouteDoc(List<RouteInfoBean> routelist) throws DataAccessException {
    for (RouteInfoBean r : routelist) {
      RouteInfoBean route = (RouteInfoBean)getObject("route.getRoute", r);
      if (route == null) {
        insert("route.insertRoutes", r);
      } else {
        r.setRouteid(route.getRouteid());
        r.setNewRoutename(r.getRoutename());
        update("route.updateRoute", r);
      }
      List<CableInfoBean> cablelist = r.getCables();
      for (CableInfoBean c : cablelist) {
        c.setRoutename(r.getRoutename());
        CableInfoBean cable = (CableInfoBean)getObject("route.getCable", c);
        if (cable == null) {
          insert("route.insertCable", c);
        } else {
          c.setCableid(cable.getCableid());
          c.setNewCablename(c.getCablename());
          update("route.updateCable", c); }
      }
    }
  }

  public TubeInfoBean getRepeatTubeList(TubeInfoBean tubeInfoBean) throws DataAccessException {
    List tubes = getObjects("route.getTubeRepeatList", tubeInfoBean);
    int total = getCount("route.getTubeRepeatCount", tubeInfoBean);
    TubeInfoBean t = new TubeInfoBean();
    t.setTubes(tubes);
    t.setTotal(Integer.valueOf(total));
    return t;
  }

  public int updateRoute(RouteInfoBean routeInfoBean) throws DataAccessException {
    return delete("route.deleteroute1", routeInfoBean); }

  public List<CableInfoBean> getcablelist(CableInfoBean cableInfoBean) throws DataAccessException {
    List cablelist = getObjects("route.getCableList", cableInfoBean);
    return cablelist; }

  public int updateCableInfo(CableInfoBean cableInfoBean) throws DataAccessException {
    return update("route.updateCable1", cableInfoBean); }

  public int deleteCable(CableInfoBean cable1) throws DataAccessException {
    int i = delete("route.deleteCableStatment", cable1);
    return i; }

  public int insertCable(CableInfoBean cableInfoBean) throws DataAccessException {
    return ((Integer)insert("route.insertCable1", cableInfoBean)).intValue(); }

  public List<CableRouteInfoBean> getCR(CableRouteInfoBean cableRouteInfoBean) throws DataAccessException {
    List resultList = getObjects("route.getCR", cableRouteInfoBean);
    return resultList;
  }

  public int updateCableRoute(CableRouteInfoBean cableRouteInfoBean) throws DataAccessException
  {
    return update("route.updateCableRouteStatment1", cableRouteInfoBean); }

  public TubeInfoBean getTubeBean(TubeInfoBean tubeInfo) throws DataAccessException {
    TubeInfoBean t = (TubeInfoBean)getObject("route.getTubeList", tubeInfo);
    return t; }

  public FaceInfoBean getFaceRelation(FaceInfoBean faceInfoBean) throws DataAccessException {
    FaceInfoBean face = (FaceInfoBean)getObject("route.getFaceRelation", faceInfoBean);
    return face;
  }

  public int deletecableroute(CableRouteInfoBean cableRouteInfoBean) throws DataAccessException
  {
    return delete("route.deleteCableRoute", cableRouteInfoBean);
  }

  public FiberBoxInfoBean getFiberlist(FiberBoxInfoBean fiberGrid) throws DataAccessException
  {
    List<FiberBoxInfoBean> lists = getObjects("route.getFiberlist", fiberGrid);
    int total = getCount("route.getFibertotal", fiberGrid);
    FiberBoxInfoBean fiber = new FiberBoxInfoBean();
    return fiber;
  }

  public DomainBean getDomain(DomainBean domain) throws DataAccessException
  {
    return ((DomainBean)getObject(GET_DOMAIN, domain));
  }

  public GeneratorInfoBean getGenerator(GeneratorInfoBean gererator)
    throws DataAccessException
  {
    return ((GeneratorInfoBean)getObject(GET_GENERATOR, gererator));
  }
}